package com.nc.unc.dao;

import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;
import com.nc.unc.model.ProductHistory;

import java.util.Map;
import java.util.Optional;

public interface ProductHistoryDao {

    Optional<ProductHistory> search(OrderItem orderItem);

    Optional<ProductHistory> search(Product product);

    void update(ProductHistory value, Integer key);

    Map<Integer, ProductHistory> getAll();

    int insert(ProductHistory t);

    Optional<ProductHistory> getByKey(Integer id);

}
