package com.nc.unc.service.impl;

import com.nc.unc.dao.CustomerDao;
import com.nc.unc.dao.impl.CustomerDaoImpl;
import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Customer;
import com.nc.unc.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class.getSimpleName());

    private final CustomerDao customers;

    public CustomerServiceImpl(CustomerDao customers){
        this.customers = customers;
        log.info("Customer Service Start");
    }

    @Override
    public void putCustomer(Customer customer) {
        customers.insert(customer);
    }

    @Override
    public Collection<Customer> searchCustomerByName(String name) throws BadRequestException {
        if (name == null)
            throw new BadRequestException();
        return customers.getAll().values();
    }

    @Override
    public Map<Integer, Customer> getAll() {
        return customers.getAll();
    }


}
