package com.nc.unc.service;

import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Customer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;


public interface CustomerService {

    void putCustomer(String name, String lastName,String phone, LocalDate localDate) throws BadRequestException;

    Collection<Customer> searchCustomerByName(String name);

    Map<Integer, Customer> getAll();
}
