package com.nc.unc.myDao;

import com.nc.unc.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemDao extends CrudDAO<OrderItem> {

    List<OrderItem> findAllByIdOrder(Number id);

    List<OrderItem> getByProductHistory(Number id);

    Optional<OrderItem> search(String name, Long price, int count);
}
