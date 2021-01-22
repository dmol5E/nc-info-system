package com.nc.unc.dao;

import com.nc.unc.model.Order;

import java.util.Map;
import java.util.Optional;

public interface OrderDao {

    void update(Order order, Integer id);

    Map<Integer, Order> getAll();

    int insert(Order order);

    Optional<Order> getByKey(Integer id);


}
