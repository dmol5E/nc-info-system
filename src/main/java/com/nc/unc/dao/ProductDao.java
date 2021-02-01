package com.nc.unc.dao;

import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;

import java.util.Map;
import java.util.Optional;

public interface ProductDao {

    void update(Product product, Integer id);

    Map<Integer, Product> getAll();

    void insert(Product product);

    Optional<Product> getByKey(Integer id);

    Optional<Product> search(OrderItem item);

}
