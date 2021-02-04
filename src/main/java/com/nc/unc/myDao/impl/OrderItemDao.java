package com.nc.unc.myDao.impl;

import com.nc.unc.model.OrderItem;

import javax.sql.DataSource;

public class OrderItemDao extends CrudDaoImpl<OrderItem> implements com.nc.unc.myDao.OrderItemDao {
    public OrderItemDao(DataSource dataSource) {
        super(dataSource);
    }
}
