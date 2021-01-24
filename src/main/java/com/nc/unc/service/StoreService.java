package com.nc.unc.service;


import com.nc.unc.model.Product;
import java.util.Map;

public interface StoreService {


    Map<Integer, Product> getAll();

    Product findById(int id);

    Product update(int id, int count);

    void put(String name, String count, String price);
}
