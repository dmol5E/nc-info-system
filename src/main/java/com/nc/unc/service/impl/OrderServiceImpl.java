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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private Customer sessionCustomer;

    private final AddressRepository addressRepository;
    private final CustomerService customerService;
    private final OrderItemRepository orderItemRepository;
    private final StoreService storeService;
    private final OrderRepository orderRepository;
    private StorageService storageService;


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
    public void createNewOrder(String address, String  zipcode) throws BadRequestException {
        if(sessionCustomer == null || address == null || storageService.size() == 0 || zipcode == null){
            throw new BadRequestException();
        }
        this.address = addressRepository
                .getEntities()
                .values()
                .stream()
                .filter(entityAddress -> entityAddress.getAddress().equals(address))
                .filter(entityAddress->entityAddress.getZipCode() == Integer.parseInt(zipcode))
                .findFirst().orElse(new Address(addressRepository.size(), address, Integer.parseInt(zipcode)));
        orderRepository.put(new Order(orderRepository.size(),
                          sessionCustomer,
                          LocalDate.now(),
                          storageService.getPrice(),
                          new ArrayList<>(storageService.get()),
                          this.address,
                          addressRepository.getByKey(0)));
        storageService = new StorageServiceImpl();
    }

    @Override
    public void findById() {

    }

    public void putOrderItem(Product product, int increase) throws BadRequestException{
        log.info("Put into order item: {}", product.toString());
        storeService.update(product.getKey(), (-1) * increase);
        storageService.putOrderItem(product, increase);
        log.info("After put into order item: {}", product.toString());
    }

    public List<OrderItem> getStorage(){return storageService.get();}

    @Override
    public Map<Integer, Order> getAll() {
        return orderRepository.getEntities();
    }

    @Override
    public void addOrderCustomer(Customer customer) {
        sessionCustomer = customer;
    }

    @Override
    public Customer getOrderCustomer() {
        return sessionCustomer;
    }

    @Override
    public double getSum() { return storageService.getPrice(); }


    public List<Product> getStore(){
        return storeService.getAll()
                .values()
                .stream()
                .filter(product -> product.getCount()!=0)
                .collect(Collectors.toList());
    }

    @Override
    public Order updateOrderStatus(int index, LocalDate date) {
        this.orderRepository.getByKey(index).setSentWhen(date);
        return this.orderRepository.getByKey(index);
    }

}
