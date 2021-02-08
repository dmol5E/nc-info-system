package com.nc.unc.myService;

import com.nc.unc.dto.CustomerDto;


import java.util.List;

public interface ICustomerService {
    List<CustomerDto> getAll();
    CustomerDto findId(int id);
    void addCustomer(CustomerDto customerDto);
}
