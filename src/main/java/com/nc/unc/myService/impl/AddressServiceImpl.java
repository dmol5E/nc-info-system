package com.nc.unc.myService.impl;

import com.nc.unc.dto.AddressDto;
import com.nc.unc.myDao.AddressDao;
import com.nc.unc.myService.IAddressService;
import com.nc.unc.myService.mapper.AddressMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AddressServiceImpl implements IAddressService {

    private AddressDao addressDao;
    private AddressMapper addressMapper;

    private String regexZipcode = "^\\d{5}(?:[-\\s]\\d{4})?$";

    @Autowired
    public void autowired(AddressDao addressDao,
                          AddressMapper addressMapper){
        this.addressDao = addressDao;
        this.addressMapper = addressMapper;
    }

    @Override
    public void insert(AddressDto address) {
        if(!checkZipcode(address.getZipcode()) || address.getAddress().equals(""))
            throw new IllegalArgumentException();
        addressDao.insert(addressMapper.toEntity(address));
    }

    @Override
    public List<AddressDto> getAll() {
        return addressDao.getAll().stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AddressDto> search(AddressDto address) {
        if(!checkZipcode(address.getZipcode()) || address.getAddress().equals(""))
            throw new IllegalArgumentException();
        return addressDao.searchByAddressZipcode(address.getAddress(), address.getZipcode()).stream()
                .map(addressMapper::toDto)
                .findFirst();
    }

    @Override
    public Optional<AddressDto> getByAddress(String address) {
        if(address.equals(""))
            throw new IllegalArgumentException();
        return Optional.empty();
    }

    @Override
    public Optional<AddressDto> getByZipcode(int zipcode) {
        if(!checkZipcode(zipcode))
            throw new IllegalArgumentException();
        return addressDao.searchByZipcode(zipcode).stream()
                .map(addressMapper::toDto)
                .findFirst();
    }

    private boolean checkZipcode(int zipcode){
        return Integer.toString(zipcode).matches(regexZipcode);
    }
}
