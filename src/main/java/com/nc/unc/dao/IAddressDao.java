package com.nc.unc.dao;

import com.nc.unc.model.Address;

import java.util.Optional;

public interface IAddressDao extends ICrudDAO<Address> {

    Optional<Address> searchByAddressZipcode(String address, String zipcode);

    Optional<Address> searchByAddress(String address);

    Optional<Address> searchByZipcode(String zipcode);
}
