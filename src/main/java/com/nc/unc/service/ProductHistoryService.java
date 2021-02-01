package com.nc.unc.service;

import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;
import com.nc.unc.model.ProductHistory;

import java.util.Map;
import java.util.Optional;

public interface ProductHistoryService {


    void put(Product product);

    Optional<ProductHistory> searchById(int id);

    Optional<ProductHistory> searchProductHistory(Product product);

    Optional<ProductHistory> searchProductHistory(OrderItem orderItem);

    Map<Integer, ProductHistory> getAll();

}
