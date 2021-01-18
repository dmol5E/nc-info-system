package com.nc.unc.repositories.impl;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.nc.unc.model.Address;

import java.beans.ConstructorProperties;
import java.util.Map;
import java.util.stream.Collectors;

@JsonRootName("AddressRepository")
public class AddressRepository extends RepositoryEntity<Long, Address> {

    public AddressRepository() {
        super(AddressRepository.class);
    }

    @JsonCreator
    public AddressRepository(String className) { super(AddressRepository.class.getSimpleName());}

    public Map<Long, Address> getAddress(String address){
        String product = address.toLowerCase();
        return this.entities.entrySet().stream()
                .filter(entry -> entry.getValue().getAddress().toLowerCase().contains(product))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public String toString() {
        return "AddressRepository{ " +
                super.toString() + " \n}";
    }
}
