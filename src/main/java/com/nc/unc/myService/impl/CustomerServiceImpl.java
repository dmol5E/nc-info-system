package com.nc.unc.myService.impl;

import com.nc.unc.dto.CustomerDto;
import com.nc.unc.exception.BadRequestException;
import com.nc.unc.exception.RequestException;
import com.nc.unc.model.Customer;
import com.nc.unc.myDao.CustomerDao;
import com.nc.unc.myService.ICustomerService;
import com.nc.unc.myService.mapper.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {

    private CustomerDao customerDao;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao,  CustomerMapper customerMapper){
        this.customerDao = customerDao;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDto> getAll() {
        log.debug("CustomerServiceImpl.getAll() was invoked");
        return customerDao.getAll()
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto findId(int id) {
        log.debug("CustomerServiceImpl.findId(int id) was invoked");
        Optional<Customer> optionalCustomer = customerDao.find(id);
        if(optionalCustomer.isEmpty()){
            log.error("No such customer by id {}", id);
            throw new RequestException("No such customer", HttpStatus.BAD_REQUEST);
        }
        return optionalCustomer.stream()
                .map(customerMapper::toDto)
                .findFirst().get();
    }

    @Override
    public void addCustomer(CustomerDto customerDto) {
        log.debug("CustomerServiceImpl.addCustomer(CustomerDto customerDto) was invoked");
        if(!checkPhoneNumber(customerDto.getPhoneNumber())) {
            log.error("Invalid phone number {}", customerDto.getPhoneNumber());
            throw new RequestException("Invalid phone number", HttpStatus.BAD_REQUEST);
        }
        customerDao.insert(customerMapper.toEntity(customerDto));
    }

    private boolean checkPhoneNumber(String phone){
        String regexPhoneNumber = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
        return phone.matches(regexPhoneNumber);
    }
}
