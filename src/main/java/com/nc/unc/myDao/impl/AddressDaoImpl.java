package com.nc.unc.myDao.impl;

import com.nc.unc.model.Address;
import com.nc.unc.myDao.AddressDao;

import javax.sql.DataSource;

public class AddressDaoImpl extends CrudDaoImpl<Address> implements AddressDao {
    public AddressDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
