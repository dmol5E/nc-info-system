package com.nc.unc.service.impl;

import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Address;
import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.repositories.impl.AddressRepository;
import com.nc.unc.repositories.impl.OrderItemRepository;
import com.nc.unc.repositories.impl.OrderRepository;
import com.nc.unc.repositories.impl.ProductRepository;
import com.nc.unc.service.CustomerService;
import com.nc.unc.service.OrderService;
import com.nc.unc.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final AddressRepository addressRepository = new AddressRepository();
    private final CustomerService customerService = new CustomerServiceImpl();
    private final OrderItemRepository orderItemRepository = new OrderItemRepository();
    private final ProductRepository productRepository = new ProductRepository();
    private final OrderRepository orderRepository = new OrderRepository();


    private Customer customer;

    private Address address;


    StorageService storageService = new StorageServiceImpl();

    public OrderServiceImpl() {
        log.info("Order Service Start");
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

    @Override
    public Map<Long, Order> getAll() {
        return orderRepository.getEntities();
    }


    @Override
    public Order updateOrderStatus(long index, LocalDate date) {
        return this.orderRepository.getByKey(index).setSentWhen(date);
    }

}
