package com.nc.unc.service;

import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;


public interface OrderService {

    void putOrderItem(Product product, int increase);

    Collection<OrderItem> getStorage();

    void putCustomer(String name, String lastName,String phone, LocalDate localDate) throws BadRequestException;

    Order createNewOrder() throws BadRequestException;

    void findById();

    Map<Long, Order> getAll();

    void addOrderCustomer(Customer customer);

    Order updateOrderStatus(long index, LocalDate date);
}
