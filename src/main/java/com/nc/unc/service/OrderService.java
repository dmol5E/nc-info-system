package com.nc.unc.service;

import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface OrderService {

    List<Product> getStore();

    void putOrderItem(Product product, int increase);

    List<OrderItem> getStorage();

    void putCustomer(String name, String lastName,String phone, LocalDate localDate) throws BadRequestException;

    void createNewOrder(String address, String zipcode) throws BadRequestException;

    void findById();

    Map<Integer, Order> getAll();

    void addOrderCustomer(Customer customer);

    Customer getOrderCustomer();

    double getSum();

    Order updateOrderStatus(int index, LocalDate date);
}
