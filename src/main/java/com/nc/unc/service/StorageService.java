package com.nc.unc.service;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;

import java.util.Collection;
import java.util.List;


public interface StorageService {
    void formAnOrder(int id);
    void putOrderItem(Product product, int increase);
    int size();
    float getPrice();
    void removeOrderItem(int id);
    List<OrderItem> get();
}
