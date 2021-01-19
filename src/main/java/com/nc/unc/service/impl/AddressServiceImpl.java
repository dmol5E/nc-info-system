package com.nc.unc.service.impl;

import com.nc.unc.dao.impl.AddressDaoImpl;
import com.nc.unc.model.Address;
import com.nc.unc.service.AddressService;

import java.util.Map;
import java.util.Optional;

public class AddressServiceImpl implements AddressService {

    private final AddressDaoImpl addressDao;

    public AddressServiceImpl(AddressDaoImpl addressDao) {
        this.addressDao = addressDao;
    }

    public Map<Integer, Address> getAll(){ return addressDao.getAll(); }

    public Optional<Address> getById(int id) { return addressDao.getByKey(id); }

    @Override
    public Address searchOrInsert(String address, String zipcode) {
        return addressDao.search(address, Integer.parseInt(zipcode))
                .orElse(addressDao.insertRecovery(
                        Address.builder()
                                .zipCode(Integer.parseInt(zipcode))
                                .address(address).build())
                );
    }
}
