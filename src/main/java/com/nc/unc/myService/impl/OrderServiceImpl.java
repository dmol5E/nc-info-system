package com.nc.unc.myService.impl;

import com.nc.unc.dao.OrderDao;
import com.nc.unc.dto.*;
import com.nc.unc.model.Customer;
import com.nc.unc.myService.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Scope("session")
public class OrderServiceImpl implements IOrderService {

    private CustomerDto sessionCustomer;

    private OrderDao orderDao;

    private IAddressService addressService;
    private ICustomerService customerService;
    private IStoreService storeService;
    private IStorageService storageService;

    @Autowired
    public void autowired(IAddressService addressService,
            ICustomerService customerService,
            IStoreService storeService,
            IStorageService storageService){
        this.addressService = addressService;
        this.customerService = customerService;
        this.storeService = storeService;
        this.storageService = storageService;
    }


    @Override
    public List<ProductDto> getStore() {
        log.debug("OrderServiceImpl.getStore() was invoked");
        return storeService.getAll();
    }

    @Override
    public void putOrderItem(int id, int increase) {
        log.debug("OrderServiceImpl.putOrderItem(int id, int increase) was invoked");

    }

    @Override
    public void removeOrderItem(int id) {

    }

    @Override
    public List<OrderItemDto> getStorage() {
        return null;
    }

    @Override
    public void putCustomer(CustomerDto customer) {
        this.sessionCustomer = customer;
    }

    @Override
    public void createNewOrder(AddressDto address) {

    }

    @Override
    public OrderDto findOrderById(int id) {
        return null;
    }

    @Override
    public void updateOrder(int id, OrderDto order) {

    }

    @Override
    public List<OrderDto> getAll() {
        return null;
    }

    @Override
    public void addOrderCustomer(CustomerDto customer) {

    }

    @Override
    public CustomerDto getOrderCustomer() {
        return null;
    }

    @Override
    public double getSum() {
        return 0;
    }

    @Override
    public OrderDto updateOrderStatus(int index, LocalDate date) {
        return null;
    }
}
