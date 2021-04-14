package com.nc.unc.service;

import com.nc.unc.dto.CustomerDto;


import java.util.List;

public interface ICustomerService {
    List<CustomerDto> getAll();
    CustomerDto findId(int id);
    void addCustomer(CustomerDto customerDto);
    List<CustomerDto> search(String name);
}
