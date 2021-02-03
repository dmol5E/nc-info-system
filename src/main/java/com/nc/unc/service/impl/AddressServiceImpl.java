package com.nc.unc.service.impl;

import com.nc.unc.dao.AddressDao;
import com.nc.unc.model.Address;
import com.nc.unc.service.AddressService;

import java.util.Map;
import java.util.Optional;

public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public Map<Integer, Address> getAll(){ return addressDao.getAll(); }

    @Override
    public Optional<Address> search(Address address) {
        return addressDao.search(address.getAddress(), address.getZipCode());
    }

    @Override
    public Optional<Address> getByAddress(String address) {
        return addressDao.searchByAddress(address);
    }

    @Override
    public Optional<Address> getByZipcode(int zipcode) {
        return addressDao.searchByZipCode(zipcode);
    }

    public Optional<Address> getById(int id) { return addressDao.getByKey(id); }

    @Override
    public Address insert(Address address) {
        return addressDao.insertRecovery(address);
    }
}
