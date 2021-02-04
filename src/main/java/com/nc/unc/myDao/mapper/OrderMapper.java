package com.nc.unc.myDao.mapper;

import com.nc.unc.enums.StatusOrder;
import com.nc.unc.model.Address;
import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.model.OrderItem;
import com.nc.unc.myDao.AddressDao;
import com.nc.unc.myDao.CustomerDao;
import com.nc.unc.myDao.OrderItemDao;
import com.nc.unc.myDao.ProductDao;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class OrderMapper implements RowMapper<Order> {

    private final RowMapper<Customer> customerDao;
    private final RowMapper<Address>  addressDao;
    private final RowMapper<OrderItem>  productDao;
    public OrderMapper(RowMapper<Customer> customerDao, RowMapper<Address> addressDao, RowMapper<OrderItem> productDao){
        this.customerDao = customerDao;
        this.addressDao = addressDao;
        this.productDao = productDao;
    }

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        return Order.builder().id(resultSet.getInt("id"))
                .customer(customerDao.mapRow(resultSet, i))
                .recipient(addressDao.mapRow(resultSet, i))
                .sender(addressDao.mapRow(resultSet, i))
                .createdWhen(resultSet.getDate(resultSet.findColumn("created_when")).toLocalDate())
                .sentWhen(resultSet.getDate(resultSet.findColumn("sent_when")).toLocalDate())
                .sum(resultSet.getFloat("sum"))
                .curStatusOrder(StatusOrder.valueOf(resultSet.getString("status")))
                .products(null)
                .build();
    }
}
