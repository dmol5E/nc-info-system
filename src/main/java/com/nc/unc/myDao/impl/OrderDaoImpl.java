package com.nc.unc.myDao.impl;

import com.nc.unc.model.Order;
import com.nc.unc.myDao.OrderDao;

import javax.sql.DataSource;

public class OrderDaoImpl extends CrudDaoImpl<Order> implements OrderDao {
    public OrderDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
