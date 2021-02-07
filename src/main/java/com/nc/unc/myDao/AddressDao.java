package com.nc.unc.myDao;

import com.nc.unc.model.Address;

import java.util.Optional;

public interface AddressDao extends CrudDAO<Address> {

    Optional<Address> searchByAddressZipcode(String address, int zipcode);

    Optional<Address> searchByAddress(String address);

    Optional<Address> searchByZipcode(int zipcode);
}
