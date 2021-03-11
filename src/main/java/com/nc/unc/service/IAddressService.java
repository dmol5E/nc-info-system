package com.nc.unc.service;

import com.nc.unc.dto.AddressDto;

import java.util.List;


public interface IAddressService {
    int insert(AddressDto address);
    List<AddressDto> getAll();
    AddressDto search(AddressDto address);
    AddressDto getByAddress(String address);
    AddressDto getByZipcode(String zipcode);
}
