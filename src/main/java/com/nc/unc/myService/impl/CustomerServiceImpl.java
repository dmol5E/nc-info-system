package com.nc.unc.myService.impl;

import com.nc.unc.dto.CustomerDto;
import com.nc.unc.myDao.CustomerDao;
import com.nc.unc.myService.ICustomerService;
import com.nc.unc.myService.mapper.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {

    private CustomerDao customerDao;
    private CustomerMapper customerMapper;

    @Autowired
    public void autowired(CustomerDao customerDao,  CustomerMapper customerMapper){
        this.customerDao = customerDao;
        this.customerMapper = customerMapper;
    }

    private String regexPhoneNumber = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    @Override
    public List<CustomerDto> getAll() {
        log.debug("CustomerServiceImpl.getAll() was invoked");
        return customerDao.getAll()
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addCustomer(CustomerDto customerDto) {
        log.debug("CustomerServiceImpl.addCustomer(CustomerDto customerDto) was invoked");
        if(!checkPhoneNumber(customerDto.getPhoneNumber()))
            //todo throw
        customerDao.insert(customerMapper.toEntity(customerDto));
    }

    private boolean checkPhoneNumber(String phone){
        return phone.matches(regexPhoneNumber);
    }
}
