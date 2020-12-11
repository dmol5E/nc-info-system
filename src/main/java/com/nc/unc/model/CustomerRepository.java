package com.nc.unc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerRepository implements Serializable {
    //TODO MAP
    private List<Customer> list = new ArrayList<>();

    public List<Customer> getAll() { return this.list; }

    public void addCustomer(Customer customer) { list.add(customer); }

    public List<Customer> getCustomer(String search){
        return list.stream()
                .filter((a) -> a.getFIO().contains(search))
                .collect(Collectors.toList());
    }

    //toDo Decorator

}
