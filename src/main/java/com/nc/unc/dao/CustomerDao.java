package com.nc.unc.dao;

import com.nc.unc.model.Customer;

import java.util.Map;
import java.util.Optional;

public interface CustomerDao {

    Map<Integer, Customer> getAll();

    void update(Customer customer, Integer id);

    void insert(Customer customer);

    Optional<Customer> getByKey(Integer id);

}
