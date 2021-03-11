package com.nc.unc.service.impl;

import com.nc.unc.dto.*;
import com.nc.unc.model.enums.StatusOrder;
import com.nc.unc.exception.RequestException;
import com.nc.unc.model.Order;
import com.nc.unc.dao.IOrderDao;
import com.nc.unc.service.*;
import com.nc.unc.service.mapper.impl.OrderMapper;
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
public class OrderServiceImpl implements IOrderService {

    private CustomerDto sessionCustomer;

    private IOrderDao orderDao;

    private OrderMapper orderMapper;
    private IAddressService addressService;
    private ICustomerService customerService;
    private IStoreService storeService;
    private IStorageService storageService;

    @Autowired
    public OrderServiceImpl(IAddressService addressService,
                            ICustomerService customerService,
                            IStoreService storeService,
                            IOrderDao orderDao,
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

        int addressId;
        try{
            addressId = addressService.search(address).getId();

        } catch (RequestException e){
            addressId = addressService.insert(address);
        }

        int id = orderDao.insert(Order.builder()
                        .fkSender(1)
                        .fkRecipient(addressId)
                        .fkCustomer(sessionCustomer.getId())
                        .createdWhen(LocalDate.now())
                        .build()).intValue();
        storageService.get().forEach(orderItemDto -> {
            ProductDto productDto = storeService.search(orderItemDto);
            log.info("productDto {}", productDto);
            storeService.decrease(productDto.getId(), productDto.getCount());
        });
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
    public void updateOrder(OrderDto order) {
        log.debug("OrderServiceImpl.updateOrder(OrderDto order) was invoked");

        Optional<Order> optionalOrder = orderDao.find(order.getId());
        if(optionalOrder.isEmpty()){
            log.error("No such order to this OrderDto");
            throw new RequestException("No such order to this OrderDto", HttpStatus.NOT_FOUND);
        }

        orderDao.update(optionalOrder.get());
    }

    @Override
    public List<OrderDto> getAll() {
        log.debug("OrderServiceImpl.getAll() was invoked");
        return orderDao.getAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getOrderCustomer() {
        log.debug("OrderServiceImpl.getOrderCustomer() was invoked");
        return sessionCustomer;
    }

    @Override
    public double getSum() {
        return storageService.getPrice();
    }

    @Override
    public OrderDto updateOrderStatus(int index, LocalDate date) {
        log.debug("OrderServiceImpl.updateOrderStatus(int index, LocalDate date) was invoked");
        Optional<Order> optionalOrder = orderDao.find(index);
        if(optionalOrder.isEmpty()){
            log.error("No such order to this OrderDto");
            throw new RequestException("No such order to this OrderDto", HttpStatus.NOT_FOUND);
        }

        if(date.isBefore(optionalOrder.get().getCreatedWhen())){
            log.error("Invalid time to sent date ");
            throw new RequestException("Invalid time to sent date", HttpStatus.NOT_FOUND);
        }


        optionalOrder.get().setSentWhen(date);
        optionalOrder.get().setCurStatusOrder(StatusOrder.SENT);
        orderDao.update(optionalOrder.get());
        return optionalOrder.stream()
                .map(orderMapper::toDto)
                .findFirst()
                .get();
    }
}
