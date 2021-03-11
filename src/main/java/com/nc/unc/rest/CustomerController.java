package com.nc.unc.rest;

import com.nc.unc.dto.CustomerDto;
import com.nc.unc.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private ICustomerService customerService;

    @Autowired
    public void autowired(ICustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public void create(@RequestBody CustomerDto customerDto){
        customerService.addCustomer(customerDto);
    }

    @GetMapping("/all")
    public List<CustomerDto> getAllCustomer(){
        return customerService.getAll();
    }
}
