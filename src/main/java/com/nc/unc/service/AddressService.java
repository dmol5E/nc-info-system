package com.nc.unc.service;

import com.nc.unc.model.Address;

import java.util.Map;
import java.util.Optional;

public interface AddressService {
    Address searchOrInsert(String address, String zipcode);

    Optional<Address> getById(int id);

    Map<Integer, Address> getAll();
}
