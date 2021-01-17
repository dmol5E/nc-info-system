package com.nc.unc.service.impl;

import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Customer;
import com.nc.unc.repositories.impl.CustomerRepository;
import com.nc.unc.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class.getSimpleName());

    private final CustomerRepository customers;

    public CustomerServiceImpl(CustomerRepository customers){
        this.customers = customers;
        log.info("Customer Service Start");
    }

    @Override
    public void putCustomer(String name, String lastName,String phone, LocalDate localDate) throws BadRequestException {
        if(name.equals("") || lastName.equals("")
                || phone.equals("") || localDate == null) {
            log.warn("Invalid Input Date Exception java request: name: {} lastname: {} phone: {} localDate: {}", name, lastName, phone, localDate);
            throw new BadRequestException();
        }
        else
            customers.put(new Customer(customers.size(), name, lastName, phone, localDate));
    }

    @Override
    public Collection<Customer> searchCustomerByName(String name) throws BadRequestException {
        if (name == null)
            throw new BadRequestException();
        return customers.getCustomer(name).values();
    }

    @Override
    public Map<Long, Customer> getAll() {
        return customers.getEntities();
    }


}
