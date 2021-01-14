package com.nc.unc.service.impl;

import com.nc.unc.model.OrderItem;
import com.nc.unc.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;


public class StorageServiceImpl implements StorageService {

    private Logger log = LoggerFactory.getLogger(StorageService.class);

    private HashMap<Long, OrderItem> storage = new HashMap<>();

    private double price;

    public StorageServiceImpl(){
        log.info("Order Service Start");
    }

    public Collection<OrderItem> getStorage() { return this.storage.values(); }

    @Override
    public int size() { return storage.size(); }

    public double getPrice() { return this.price; }

    public void putOrderItem(OrderItem newStorageItem) {
        if(this.storage.containsKey(newStorageItem.getKey())) {
            storage.get(newStorageItem.getKey()).setCount(newStorageItem.getCount() + storage.get(newStorageItem.getKey()).getCount());
        } else {
            storage.put(newStorageItem.getKey(), newStorageItem);
            price += newStorageItem.getCount() * newStorageItem.getProduct().getPrice();
        }
    }

    @Override
    public Collection<OrderItem> get() { return storage.values(); }

    public void removeOrderItem(OrderItem storageItem) {
        this.storage.remove(storageItem.getKey());
    }
}
