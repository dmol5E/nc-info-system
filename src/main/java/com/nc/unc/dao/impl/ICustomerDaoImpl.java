package com.nc.unc.dao.impl;

import com.nc.unc.model.Customer;
import com.nc.unc.dao.ICustomerDao;
import org.springframework.stereotype.Repository;

@Repository
public class ICustomerDaoImpl extends CrudDaoImpl<Customer> implements ICustomerDao {

}
