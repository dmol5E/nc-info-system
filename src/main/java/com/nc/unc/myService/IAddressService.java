package com.nc.unc.myService;

import com.nc.unc.dto.AddressDto;
import com.nc.unc.model.Address;

import java.util.List;
import java.util.Optional;


public interface IAddressService {
    void insert(AddressDto address);
    List<AddressDto> getAll();
    Optional<AddressDto> search(AddressDto address);
    Optional<AddressDto> getByAddress(String address);
    Optional<AddressDto> getByZipcode(int zipcode);
}
