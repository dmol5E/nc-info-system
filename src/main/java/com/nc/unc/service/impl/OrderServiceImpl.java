package com.nc.unc.service.impl;

import com.nc.unc.dao.OrderDao;
import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.*;
import com.nc.unc.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private Customer sessionCustomer;

    private final OrderDao orderDao;

    private final AddressService addressService;
    private final CustomerService customerService;
    private final StoreService storeService;
    private StorageService storageService;


    public OrderServiceImpl(OrderDao orderRepository,
                            StoreService storeService,
                            AddressService addressRepository,
                            CustomerService customerService) {
        log.info("Order Service Start");
        storageService = new StorageServiceImpl(storeService);
        this.customerService = customerService;
        this.orderDao = orderRepository;
        this.storeService = storeService;
        this.addressService = addressRepository;
    }


    @Override
    public void putCustomer(Customer customer) {
        customerService.putCustomer(customer);
    }

    @Override
    public void createNewOrder(Address address) throws BadRequestException {
        if(sessionCustomer == null || address == null || storageService.size() == 0){
            throw new BadRequestException();
        }
        Address addressSession = addressService.search(address).orElse(addressService.insert(address));
        storageService.formAnOrder(
                orderDao.insert(Order.builder()
                        .customer(sessionCustomer)
                        .createdWhen(LocalDate.now())
                        .sum(storageService.getPrice())
                        .recipient(addressSession)
                        .sender(addressService.getById(1).orElseThrow())
                        .build()
        ));
        storageService.get().forEach(orderItem -> storeService.update(storeService.search(orderItem).orElseThrow().getKey(),(-1) * orderItem.getCount()));
        storageService = new StorageServiceImpl(storeService);
    }

    @Override
    public Order findOrderById(int id) {
        return orderDao.getByKey(id).orElse(null);
    }

    @Override
    public void updateOrder(int id, Order order) {
        orderDao.update(order, id);
    }

    public void putOrderItem(int id, int increase) {
        Product productInDB = storeService.findById(id);
        if(productInDB.getCount() < Math.abs(increase))
            throw new BadRequestException();
        storageService.putOrderItem(productInDB, increase);
    }

    @Override
    public void removeOrderItem(int id) {
        storageService.removeOrderItem(id);
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
