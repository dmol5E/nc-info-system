package com.nc.unc.service;


import com.nc.unc.model.Product;
import java.util.Map;

public interface StoreService {

    Map<Long, Product> getAll();

    Product findById(Long id);

    Product update(Long id, int count);

    int size();

    void put(String name, String count, String price);
}
