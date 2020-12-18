package com.nc.unc.repositories;

import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.model.Status;

import java.util.Map;
import java.util.stream.Collectors;

public class OrderRepository extends RepositoryEntity<Long, Order> {
    public OrderRepository() {
        super(OrderRepository.class);
    }

    public Map<Long, Order> getOrdersByCustomer(Customer customer){
        return this.entities.entrySet().stream()
                .filter(entry -> entry.getValue().getCustomer().equals(customer))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    public Map<Long, Order> getOrdersByCurStatus(Status status){
        return this.entities.entrySet().stream()
                .filter(entry -> entry.getValue().getCurStatus() == status)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }


}
