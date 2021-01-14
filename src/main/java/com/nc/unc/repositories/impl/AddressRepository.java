package com.nc.unc.repositories.impl;

import com.nc.unc.model.Address;

import java.util.Map;
import java.util.stream.Collectors;

public class AddressRepository extends RepositoryEntity<Long, Address> {

    public AddressRepository() {
        super(AddressRepository.class);
    }

    public Map<Long, Address> getAddress(String address){
        String product = address.toLowerCase();
        return this.entities.entrySet().stream()
                .filter(entry -> entry.getValue().getAddress().toLowerCase().contains(product))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
