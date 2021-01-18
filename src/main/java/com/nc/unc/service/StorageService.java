package com.nc.unc.service;

import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;

import java.util.Collection;
import java.util.List;


public interface StorageService {


    void putOrderItem(Product product, int increase);

    int size();

    double getPrice();

    void removeOrderItem(OrderItem storageItem);

    List<OrderItem> get();
}
