package com.nc.unc.service;

import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface OrderService {

    List<Product> getStore();

    void putOrderItem(int id, int increase);

    void removeOrderItem(int id);

    List<OrderItem> getStorage();

    void putCustomer(Customer customer);

    void createNewOrder(Address address);

    Order findOrderById(int id);

    void updateOrder(int id, Order order);

    Map<Integer, Order> getAll();

    void addOrderCustomer(Customer customer);

    Customer getOrderCustomer();

    double getSum();

    Order updateOrderStatus(int index, LocalDate date);
}
