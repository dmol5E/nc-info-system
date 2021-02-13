package com.nc.unc.myDao.impl;

import com.nc.unc.model.Customer;
import com.nc.unc.myDao.ICustomerDao;
import org.springframework.stereotype.Repository;

@Repository
public class ICustomerDaoImpl extends CrudDaoImpl<Customer> implements ICustomerDao {

}
