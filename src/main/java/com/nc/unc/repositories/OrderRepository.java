package com.nc.unc.repositories;

import com.nc.unc.model.Order;

public class OrderRepository extends Repository<Long, Order> {
    protected OrderRepository(String fileName) {
        super(fileName, OrderRepository.class);
    }

}
