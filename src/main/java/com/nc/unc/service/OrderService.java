package com.nc.unc.service;

import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;

import java.time.LocalDate;
import java.util.Map;


public interface OrderService {

    void putCustomer(String name, String lastName,String phone, LocalDate localDate) throws BadRequestException;

    Order createNewOrder() throws BadRequestException;

    void findById();

    Map<Long, Order> getAll();

    Order updateOrderStatus(long index, LocalDate date);
}
