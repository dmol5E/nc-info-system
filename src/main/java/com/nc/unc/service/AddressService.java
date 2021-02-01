package com.nc.unc.service;

import com.nc.unc.model.Address;

import java.util.Map;
import java.util.Optional;

public interface AddressService {
    Address insert(Address address);

    Optional<Address> getById(int id);

    Map<Integer, Address> getAll();

    Optional<Address> search(Address address);

    Optional<Address> getByAddress(String address);

    Optional<Address> getByZipcode(int zipcode);
}
