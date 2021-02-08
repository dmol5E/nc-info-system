package com.nc.unc.myService.impl;

import com.nc.unc.dto.*;
import com.nc.unc.exception.RequestException;
import com.nc.unc.model.Address;
import com.nc.unc.model.Order;
import com.nc.unc.myDao.OrderDao;
import com.nc.unc.myService.*;
import com.nc.unc.myService.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Scope("session")
public class OrderServiceImpl implements IOrderService {

    private CustomerDto sessionCustomer;

    private OrderDao orderDao;

    private OrderMapper orderMapper;
    private IAddressService addressService;
    private ICustomerService customerService;
    private IStoreService storeService;
    private IStorageService storageService;

    @Autowired
    public OrderServiceImpl(IAddressService addressService,
                            ICustomerService customerService,
                            IStoreService storeService,
                            OrderDao orderDao,
                            OrderMapper orderMapper,
                            IStorageService storageService){
        this.orderMapper = orderMapper;
        this.addressService = addressService;
        this.customerService = customerService;
        this.storeService = storeService;
        this.orderDao = orderDao;
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
        storageService.putOrderItem(storeService.findById(id), increase);
    }

    @Override
    public void removeOrderItem(int id) {
        log.debug("OrderServiceImpl.putOrderItem(int id, int increase) was invoked");
        storageService.removeOrderItem(id);
    }

    @Override
    public List<OrderItemDto> getStorage() {
        log.debug("OrderServiceImpl.getStorage() was invoked");
        return storageService.get();
    }

    @Override
    public void putCustomer(CustomerDto customer) {
        log.debug("OrderServiceImpl.putOrderItem(int id, int increase) was invoked");
        this.sessionCustomer = customerService.findId(customer.getId());
    }

    @Override
    public void createNewOrder(AddressDto address) {
        log.debug("OrderServiceImpl.putOrderItem(int id, int increase) was invoked");
        if(address == null){
            log.error("Null pointer address");
            throw new RequestException("Null pointer address", HttpStatus.BAD_REQUEST);
        }

        AddressDto addressFromDB = addressService.search(address);
        int addressId = addressFromDB == null ? addressService.insert(address) : addressFromDB.getId();
        int id = orderDao.insert(Order.builder()
                        .fkSender(1)
                        .fkRecipient(addressId)
                        .fkCustomer(sessionCustomer.getId())
                        .createdWhen(LocalDate.now())
                        .build()).intValue();
        storageService.get().forEach(orderItemDto -> storeService.decrease(storeService.search(orderItemDto).getId(), orderItemDto.getCount()));
        storageService.formAnOrder(id);
    }

    @Override
    public OrderDto findOrderById(int id) {
        log.debug("OrderServiceImpl.findOrderById(int id was invoked");
        Optional<Order> optionalOrder = orderDao.find(id);
        if(optionalOrder.isEmpty()){
            log.debug("No such order by id {}", id);
            throw new RequestException("Null pointer address", HttpStatus.NOT_FOUND);
        }
        return optionalOrder.stream()
                .map(orderMapper::toDto)
                .findFirst().get();
    }

    @Override
    public void updateOrder(int id, OrderDto order) {

    }

    @Override
    public List<OrderDto> getAll() {
        return orderDao.getAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getOrderCustomer() {
        return null;
    }

    @Override
    public double getSum() {
        return storageService.getPrice();
    }

    @Override
    public OrderDto updateOrderStatus(int index, LocalDate date) {
        return null;
    }
}
