package com.nc.unc.service.impl;

import com.nc.unc.dto.CustomerDto;

import com.nc.unc.exception.RequestException;
import com.nc.unc.model.Customer;
import com.nc.unc.dao.ICustomerDao;
import com.nc.unc.service.ICustomerService;
import com.nc.unc.service.mapper.impl.CustomerMapper;
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

    private ICustomerDao ICustomerDao;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(ICustomerDao ICustomerDao, CustomerMapper customerMapper){
        this.ICustomerDao = ICustomerDao;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDto> getAll() {
        log.debug("CustomerServiceImpl.getAll() was invoked");
        return ICustomerDao.getAll()
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto findId(int id) {
        log.debug("CustomerServiceImpl.findId(int id) was invoked");
        Optional<Customer> optionalCustomer = ICustomerDao.find(id);
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
        ICustomerDao.insert(customerMapper.toEntity(customerDto));
    }

    @Override
    public List<CustomerDto> search(String name) {
        log.debug("CustomerServiceImpl.search(String name)  was invoked");
        return ICustomerDao.findByName(name)
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    private boolean checkPhoneNumber(String phone){
        String regexPhoneNumber = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
        return phone.matches(regexPhoneNumber);
    }
}
