package com.nc.unc.service.impl;

import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.*;
import com.nc.unc.repositories.impl.AddressRepository;
import com.nc.unc.repositories.impl.OrderItemRepository;
import com.nc.unc.repositories.impl.OrderRepository;
import com.nc.unc.repositories.impl.ProductRepository;
import com.nc.unc.service.CustomerService;
import com.nc.unc.service.OrderService;
import com.nc.unc.service.StorageService;
import com.nc.unc.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private Customer sessionCustomer;

    private final AddressRepository addressRepository;
    private final CustomerService customerService;
    private final OrderItemRepository orderItemRepository;
    private final StoreService storeService;
    private final OrderRepository orderRepository;
    private final StorageService storageService;

    private Customer customer;

    private Address address;

    public OrderServiceImpl(OrderRepository orderRepository,
                            StoreService storeService,
                            AddressRepository addressRepository,
                            CustomerService customerService,
                            OrderItemRepository orderItemRepository) {
        log.info("Order Service Start");
        storageService = new StorageServiceImpl();
        this.orderItemRepository = orderItemRepository;
        this.customerService = customerService;
        this.orderRepository = orderRepository;
        this.storeService = storeService;
        this.addressRepository = addressRepository;
    }


    @Override
    public void putCustomer(String name, String lastName, String phone, LocalDate localDate) throws BadRequestException {
        customerService.putCustomer(name, lastName, phone, localDate);
    }

    @Override
    public Order createNewOrder() throws BadRequestException {
        if(customer == null || address == null || storageService.size() == 0){
            throw new BadRequestException();
        }
        return new Order(addressRepository.size(), customer, LocalDate.now(), storageService.getPrice(),new ArrayList<>(storageService.get()), address, addressRepository.getByKey(0L));
    }

    @Override
    public void findById() {

    }

    public void putOrderItem(Product product, int increase){
        storageService.putOrderItem(new OrderItem((long) storageService.size(), product, increase));
    }

    public Collection<OrderItem> getStorage(){return storageService.get();}

    @Override
    public Map<Long, Order> getAll() {
        return orderRepository.getEntities();
    }

    @Override
    public void addOrderCustomer(Customer customer) {
        sessionCustomer = customer;
    }


    @Override
    public Order updateOrderStatus(long index, LocalDate date) {
        return this.orderRepository.getByKey(index).setSentWhen(date);
    }

}
