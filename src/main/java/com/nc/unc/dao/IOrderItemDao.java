package com.nc.unc.dao;

import com.nc.unc.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface IOrderItemDao extends ICrudDAO<OrderItem> {

    List<OrderItem> findAllByIdOrder(Number id);

    List<OrderItem> getByProductHistory(Number id);

    Optional<OrderItem> search(String name, Long price, int count);
}
