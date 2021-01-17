package com.nc.unc.repositories.impl;

import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.enums.StatusOrder;

import java.beans.ConstructorProperties;
import java.util.Map;
import java.util.stream.Collectors;


public class OrderRepository extends RepositoryEntity<Long, Order> {

    public OrderRepository() {
        super(OrderRepository.class);
    }

    @ConstructorProperties({"type"})
    public OrderRepository(String className){super(className);}

    public Map<Long, Order> getOrdersByCustomer(Customer customer){
        return this.entities.entrySet().stream()
                .filter(entry -> entry.getValue().getCustomer().equals(customer))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    public Map<Long, Order> getOrdersByCurStatus(StatusOrder statusOrder){
        return this.entities.entrySet().stream()
                .filter(entry -> entry.getValue().getCurStatus() == statusOrder)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    @Override
    public String toString() {
        return "OrderRepository{ " +
                super.toString() + " \n}";
    }

}
