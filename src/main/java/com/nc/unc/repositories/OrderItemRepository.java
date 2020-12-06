package com.nc.unc.repositories;

import com.nc.unc.model.OrderItem;

public class OrderItemRepository extends Repository<Long, OrderItem> {
    public OrderItemRepository(String fileName) {
        super(fileName, OrderItemRepository.class);
    }
}
