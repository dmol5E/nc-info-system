package com.nc.unc.service.impl;

import com.nc.unc.dao.impl.AddressDaoImpl;
import com.nc.unc.dao.impl.CustomerDaoImpl;
import com.nc.unc.dao.impl.OrderDaoImpl;
import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.*;
import com.nc.unc.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private Customer sessionCustomer;

    private final OrderDaoImpl orderDao;

    private final AddressService addressService;
    private final CustomerService customerService;
    private final StoreService storeService;
    private StorageService storageService;


    public OrderServiceImpl(OrderDaoImpl orderRepository,
                            StoreService storeService,
                            AddressService addressRepository,
                            CustomerService customerService) {
        log.info("Order Service Start");
        storageService = new StorageServiceImpl();
        this.customerService = customerService;
        this.orderDao = orderRepository;
        this.storeService = storeService;
        this.addressService = addressRepository;
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
        Address addressSession = addressService.searchOrInsert(address, zipcode);

        orderDao.insert(Order.builder()
                .customer(sessionCustomer)
                .createdWhen(LocalDate.now())
                .sum(storageService.getPrice())
                .products(new ArrayList<>(storageService.get()))
                .recipient(addressSession)
                .sender(addressService.getById(1).get())
                .build()
        );
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
        return orderDao.getAll();
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
        Order order = orderDao.getByKey(index).get();
        order.setSentWhen(date);

        orderDao.update(order, order.getKey());
        return order;
    }

}
