package com.nc.unc.repositories.impl;

import com.nc.unc.model.OrderItem;

public class OrderItemRepository extends RepositoryEntity<Long, OrderItem> {

    public OrderItemRepository() {
        super(OrderItemRepository.class);
    }

}
