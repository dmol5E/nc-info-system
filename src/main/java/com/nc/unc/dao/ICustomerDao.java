package com.nc.unc.dao;

import com.nc.unc.model.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerDao extends ICrudDAO<Customer> {
    List<Customer> findByName(String name);
}
