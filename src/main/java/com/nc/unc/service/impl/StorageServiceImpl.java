package com.nc.unc.service.impl;

import com.nc.unc.dao.impl.ProductDaoImpl;
import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;
import com.nc.unc.service.StorageService;
import com.nc.unc.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;


public class StorageServiceImpl implements StorageService {

    private Logger log = LoggerFactory.getLogger(StorageService.class.getSimpleName());

    private final HashMap<Integer, OrderItem> storage;
    private final StoreService storeService;
    private double price;

    public StorageServiceImpl(StoreService productDao) {
        this.storeService = productDao;
        storage = new HashMap<>();
        log.info("Order Service Start");
    }

    public List<OrderItem> getStorage() {
        return this.storage.values().stream()
                .filter(orderItem -> orderItem.getCount() != 0)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public float getPrice() {
        log.info("Storage: {}", storage);
        return storage.values()
                .stream()
                .filter(orderItem -> orderItem.getCount() != 0)
                .reduce(0.f,
                        (x, y) -> x + y.getPrice() * y.getCount(),
                        Float::sum);
    }

    @Override
    public void putOrderItem(Product productInDB, int increase) {
        OrderItem orderItem = storage.values().stream()
                .filter(storageItem -> productInDB.equals(storageItem.getName()))
                .findFirst()
                .orElse(null);

        if (orderItem != null) {
            if (orderItem.getCount() + increase > productInDB.getCount()
                    || orderItem.getCount() + increase < 0)
                throw new BadRequestException();
            orderItem.setCount(orderItem.getCount() + increase);

            if(storage.get(orderItem.getKey()).getCount() == 0)
                storage.remove(orderItem.getKey());

        } else {
            if(increase > productInDB.getCount())
                throw new BadRequestException();
            OrderItem newStorageItem = OrderItem.builder()
                    .key(this.size())
                    .name(productInDB.getName())
                    .price(productInDB.getPrice())
                    .count(increase)
                    .build();
            storage.put(newStorageItem.getKey(), newStorageItem);
        }
    }

    @Override
    public List<OrderItem> get() {
        return this.storage.values().stream()
                .filter(orderItem -> orderItem.getCount() != 0)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<OrderItem> getToCreate() {
        //storage.values().forEach(orderItem
        //        -> storeService.update(orderItem..().getKey(), (-1) * orderItem.getCount()));
        //return storage.values();
        //todo update product table
        return null;
    }

    public void removeOrderItem(OrderItem storageItem) {
        this.storage.remove(storageItem.getKey());
    }

}
