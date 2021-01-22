package com.nc.unc.dao;

import com.nc.unc.model.OrderItem;
import com.nc.unc.model.ProductHistory;

import java.util.Map;
import java.util.Optional;

public interface ProductHistoryDao {

    Optional<ProductHistory> search(OrderItem orderItem);

    void update(ProductHistory value, Integer key);

    Map<Integer, ProductHistory> getAll();

    int insert(ProductHistory t);

    Optional<ProductHistory> getByKey(Integer id);

}
