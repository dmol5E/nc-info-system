package com.nc.unc.service.impl;

import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;
import com.nc.unc.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class StorageServiceImpl implements StorageService {

    private Logger log = LoggerFactory.getLogger(StorageService.class.getSimpleName());

    private final HashMap<Integer, OrderItem> storage;

    private double price;

    public StorageServiceImpl(){
        storage = new HashMap<>();
        log.info("Order Service Start");
    }

    public List<OrderItem> getStorage() {
        return this.storage.values().stream()
                .filter(orderItem -> orderItem.getCount()!=0)
                .collect(Collectors.toList());
    }

    @Override
    public int size() { return storage.size(); }

    @Override
    public float getPrice() {
        log.info("Storage: {}", storage);
        return storage.values()
            .stream()
                .filter(orderItem -> orderItem.getCount() !=0)
                .reduce(0.f,
                        (x,y) -> x + y.getPrice()*y.getCount(),
                        Float::sum);
    }

    @Override
    public void putOrderItem(Product product, int increase) {
        OrderItem orderItem = storage.values().stream()
                .filter(storageItem -> product.equals(storageItem.getProduct()))
                .findFirst().orElse(null);
        if(orderItem != null) {
            orderItem.setCount(orderItem.getCount() + increase);
        } else {
            OrderItem newStorageItem = new OrderItem(this.size(),product, increase);
            storage.put(newStorageItem.getKey(), newStorageItem);
        }
    }

    @Override
    public List<OrderItem> get() {
        return this.storage.values().stream()
                .filter(orderItem -> orderItem.getCount()!=0)
                .collect(Collectors.toList());
    }

    public void removeOrderItem(OrderItem storageItem) {
        this.storage.remove(storageItem.getKey());
    }

}
