package com.nc.unc.repositories.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.nc.unc.model.Customer;


import java.util.Map;
import java.util.stream.Collectors;


public class CustomerRepository extends RepositoryEntity<Long, Customer> {

    public CustomerRepository() {
        super(CustomerRepository.class);
    }

    @JsonCreator
    public CustomerRepository(String className){super(CustomerRepository.class.getSimpleName());}

    public Map<Long, Customer> getCustomer(String search){
        String sought = search.toLowerCase();
        return this.entities.entrySet().stream()
                .filter(entry -> entry.getValue().getFirstName().toLowerCase().contains(sought))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    @Override
    public String toString() {
        return "CustomerRepository{ " +
                super.toString() + " \n}";
    }

}
