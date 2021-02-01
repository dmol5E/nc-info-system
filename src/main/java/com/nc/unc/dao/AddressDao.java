package com.nc.unc.dao;

import com.nc.unc.model.Address;

import java.util.Map;
import java.util.Optional;

public interface AddressDao {

    void update(Address address, Integer id);

    Map<Integer, Address> getAll();

    Address insertRecovery(Address address);

    void insert(Address address);

    Optional<Address> search(String address, int zipcode);

    Optional<Address> getByKey(Integer id);

    Optional<Address> searchByAddress(String address);

    Optional<Address> searchByZipCode(int zipcode);
}
