package com.nc.unc.dao;

import com.nc.unc.model.OrderItem;

import java.util.Map;
import java.util.Optional;

public interface OrderItemDao {

    Map<Integer, OrderItem> getByProductHistory(int id);

    Map<Integer, OrderItem> getByOrderId(int id);

    Optional<OrderItem> search(OrderItem orderItem);

    void update(OrderItem value, Integer key);

    Map<Integer, OrderItem> getAll();

    void insert(OrderItem t, int productHistoryId, int orderId);

    Optional<OrderItem> getByKey(Integer id);

}
