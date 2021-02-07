package com.nc.unc.myDao.impl;

import com.nc.unc.model.Customer;
import com.nc.unc.myDao.CustomerDao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class CustomerDaoImpl extends CrudDaoImpl<Customer> implements CustomerDao {

}
