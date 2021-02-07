package com.nc.unc.rest;

import com.nc.unc.dto.CustomerDto;
import com.nc.unc.myService.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
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
