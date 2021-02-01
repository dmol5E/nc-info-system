package com.nc.unc.service;


import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;
import com.nc.unc.model.ProductHistory;

import java.util.Map;
import java.util.Optional;

public interface StoreService {

    Optional<ProductHistory> searchProductHistory(OrderItem orderItem);

    Map<Integer, Product> getAll();

    Product findById(int id);

    Product update(int id, int count);

    void put(String name, String count, String price);

    Optional<Product> search(OrderItem orderItem);
}
