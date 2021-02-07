package com.nc.unc.myDao.mapper;

import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.myDao.AddressDao;
import com.nc.unc.myDao.CustomerDao;
import com.nc.unc.myDao.OrderItemDao;
import com.nc.unc.myDao.impl.AddressDaoImpl;
import com.nc.unc.myDao.impl.CustomerDaoImpl;
import com.nc.unc.myDao.impl.OrderItemDaoImpl;
import com.nc.unc.myDao.template.EntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderDaoMapper extends AbstractMapper<Order> {

    private AddressDao addressDao;
    private OrderItemDao orderItemDao;
    private CustomerDao customerDao;

    @Autowired
    private void set(AddressDaoImpl addressDao, OrderItemDaoImpl productDao, CustomerDaoImpl customerDao){
        this.addressDao = addressDao;
        this.customerDao = customerDao;
        this.orderItemDao = productDao;
    }

    public OrderDaoMapper() {
        super(Order.class, new EntityImpl<>(Order.class));
    }

    @Override
    public Order mapRow(ResultSet rs, int i) throws SQLException {
        Order order = super.mapRow(rs, i);
        if(order != null) {
            order.setRecipient(addressDao.find(order.getFkRecipient()).orElseThrow());
            order.setSender(addressDao.find(order.getFkSender()).orElseThrow());
            order.setCustomer(customerDao.find(order.getFkCustomer()).orElseThrow());
            order.setProducts(orderItemDao.findAllByIdOrder(order.getId()));
        }
        return order;
    }
}
