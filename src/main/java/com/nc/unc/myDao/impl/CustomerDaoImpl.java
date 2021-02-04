package com.nc.unc.myDao.impl;

import com.nc.unc.model.Customer;
import com.nc.unc.myDao.CustomerDao;
import com.nc.unc.myDao.mapper.CustomerMapper;

import javax.sql.DataSource;

public class CustomerDaoImpl extends CrudDaoImpl<Customer> implements CustomerDao {
    public CustomerDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
