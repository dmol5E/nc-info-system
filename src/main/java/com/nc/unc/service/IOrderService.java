package com.nc.unc.service;

import com.nc.unc.dto.*;
import java.time.LocalDate;
import java.util.List;



public interface IOrderService {
    List<ProductDto> getStore();
    void putOrderItem(int id, int increase);
    void removeOrderItem(int id);
    List<OrderItemDto> getStorage();
    void putCustomer(CustomerDto customer);
    void createNewOrder(AddressDto address);
    OrderDto findOrderById(int id);
    void updateOrder(OrderDto order);
    List<OrderDto> getAll();
    CustomerDto getOrderCustomer();
    double getSum();
    OrderDto updateOrderStatus(int index, LocalDate date);
}
