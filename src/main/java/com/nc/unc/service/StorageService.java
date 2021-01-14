package com.nc.unc.service;

import com.nc.unc.model.OrderItem;

import java.util.Collection;


public interface StorageService {

    int size();

    double getPrice();

    void removeOrderItem(OrderItem storageItem);

    void putOrderItem(OrderItem newStorageItem);

    Collection<OrderItem> get();
}
