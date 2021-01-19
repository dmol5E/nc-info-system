package com.nc.unc.repositories.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.enums.StatusOrder;

import java.util.Map;
import java.util.stream.Collectors;


public class OrderRepository extends RepositoryEntity<Integer, Order> {

    public OrderRepository() {
        super(OrderRepository.class);
    }

    @JsonCreator
    public OrderRepository(String className){super(OrderRepository.class.getSimpleName());}

    public Map<Integer, Order> getOrdersByCustomer(Customer customer){
        return this.entities.entrySet().stream()
                .filter(entry -> entry.getValue().getCustomer().equals(customer))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    public Map<Integer, Order> getOrdersByCurStatus(StatusOrder statusOrder){
        return this.entities.entrySet().stream()
                .filter(entry -> entry.getValue().getCurStatusOrder() == statusOrder)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    @Override
    public String toString() {
        return "OrderRepository{ " +
                super.toString() + " \n}";
    }

}
