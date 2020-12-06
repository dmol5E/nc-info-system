package com.nc.unc.repositories;

import com.nc.unc.model.Address;

public class AddressRepository extends Repository<Long, Address> {

    public AddressRepository(String fileName) {
        super(fileName, AddressRepository.class);
    }


}
