package com.nc.unc.myService;

import com.nc.unc.dto.*;
import com.nc.unc.model.Address;
import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.model.OrderItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface IOrderService {
    List<ProductDto> getStore();
    void putOrderItem(int id, int increase);
    void removeOrderItem(int id);
    List<OrderItemDto> getStorage();
    void putCustomer(CustomerDto customer);
    void createNewOrder(AddressDto address);
    OrderDto findOrderById(int id);
    void updateOrder(int id, OrderDto order);
    List<OrderDto> getAll();
    CustomerDto getOrderCustomer();
    double getSum();
    OrderDto updateOrderStatus(int index, LocalDate date);
}
