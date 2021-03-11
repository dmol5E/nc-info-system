package com.nc.unc.dao.mapper;

import com.nc.unc.model.Order;
import com.nc.unc.dao.IAddressDao;
import com.nc.unc.dao.ICustomerDao;
import com.nc.unc.dao.IOrderItemDao;
import com.nc.unc.dao.impl.IAddressDaoImpl;
import com.nc.unc.dao.impl.ICustomerDaoImpl;
import com.nc.unc.dao.impl.OrderItemDaoImpl;
import com.nc.unc.dao.template.EntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderDaoMapper extends AbstractMapper<Order> {

    private IAddressDao IAddressDao;
    private IOrderItemDao orderItemDao;
    private ICustomerDao ICustomerDao;

    @Autowired
    private void set(IAddressDaoImpl addressDao, OrderItemDaoImpl productDao, ICustomerDaoImpl customerDao){
        this.IAddressDao = addressDao;
        this.ICustomerDao = customerDao;
        this.orderItemDao = productDao;
    }

    public OrderDaoMapper() {
        super(Order.class, new EntityImpl<>(Order.class));
    }

    @Override
    public Order mapRow(ResultSet rs, int i) throws SQLException {
        Order order = super.mapRow(rs, i);
        if(order != null) {
            order.setRecipient(IAddressDao.find(order.getFkRecipient()).orElseThrow());
            order.setSender(IAddressDao.find(order.getFkSender()).orElseThrow());
            order.setCustomer(ICustomerDao.find(order.getFkCustomer()).orElseThrow());
            order.setProducts(orderItemDao.findAllByIdOrder(order.getId()));
        }
        return order;
    }
}
