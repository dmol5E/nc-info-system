package com.nc.unc.myDao.mapper;

import com.nc.unc.model.Order;
import com.nc.unc.myDao.IAddressDao;
import com.nc.unc.myDao.ICustomerDao;
import com.nc.unc.myDao.IOrderItemDao;
import com.nc.unc.myDao.impl.IAddressDaoImpl;
import com.nc.unc.myDao.impl.ICustomerDaoImpl;
import com.nc.unc.myDao.impl.OrderItemDaoImpl;
import com.nc.unc.myDao.template.EntityImpl;
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
