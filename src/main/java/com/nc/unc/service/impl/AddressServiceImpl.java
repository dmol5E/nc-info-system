package com.nc.unc.service.impl;

import com.nc.unc.dto.AddressDto;
import com.nc.unc.exception.RequestException;
import com.nc.unc.model.Address;
import com.nc.unc.dao.IAddressDao;
import com.nc.unc.service.IAddressService;
import com.nc.unc.service.mapper.impl.AddressMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AddressServiceImpl implements IAddressService {

    private AddressMapper addressMapper;

    private IAddressDao IAddressDao;

    @Autowired
    public AddressServiceImpl(IAddressDao IAddressDao,
                              AddressMapper addressMapper){
        this.IAddressDao = IAddressDao;
        this.addressMapper = addressMapper;
    }

    @Override
    public int insert(AddressDto address) {
        log.debug("AddressServiceImpl.findId(int id) was invoked");
        if(!checkZipcode(address.getZipcode()) || address.getAddress().equals("")) {
            log.error("Invalid date zipcode {} address {} ", address.getZipcode(), address.getAddress());
            throw new RequestException("Invalid date", HttpStatus.BAD_REQUEST);
        }
        return IAddressDao.insert(addressMapper.toEntity(address)).intValue();
    }

    @Override
    public List<AddressDto> getAll() {
        log.debug("AddressServiceImpl.getAll() was invoked");
        return IAddressDao.getAll().stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto search(AddressDto address) {
        log.debug("AddressServiceImpl.search(AddressDto address) was invoked");
        if(!checkZipcode(address.getZipcode()) || address.getAddress().equals("")) {
            log.error("Invalid date zipcode {} address {} ", address.getZipcode(), address.getAddress());
            throw new RequestException("Invalid date", HttpStatus.BAD_REQUEST);
        }

        Optional<Address> optionalAddress = IAddressDao.searchByAddressZipcode(address.getAddress(), address.getZipcode());
        if(optionalAddress.isEmpty()){
            log.error("No such element zipcode {} address {} ", address.getZipcode(), address.getAddress());
            throw new RequestException("Invalid date", HttpStatus.NOT_FOUND);
        }

        return optionalAddress.stream()
                .map(addressMapper::toDto)
                .findFirst().get();
    }

    @Override
    public AddressDto getByAddress(String address) {
        log.debug("AddressServiceImpl.getByAddress(String address was invoked");
        if(address.equals("")) {
            log.error("Invalid date address {} ", address);
            throw new RequestException("Invalid date", HttpStatus.BAD_REQUEST);
        }

        Optional<Address> optionalAddress = IAddressDao.searchByAddress(address);
        if (optionalAddress.isEmpty()){
            log.error("No such element by address {} ", address);
            throw new RequestException("Invalid date", HttpStatus.NOT_FOUND);
        }

        return optionalAddress.stream()
                .map(addressMapper::toDto)
                .findFirst().get();
    }

    @Override
    public AddressDto getByZipcode(String zipcode) {
        log.debug("AddressServiceImpl.getByZipcode(int zipcode) was invoked");
        if(!checkZipcode(zipcode)) {
            log.error("Invalid date address {} ", zipcode);
            throw new RequestException("Invalid date", HttpStatus.BAD_REQUEST);
        }

        Optional<Address> optionalAddress = IAddressDao.searchByZipcode(zipcode);
        if (optionalAddress.isEmpty()){
            log.error("No such element by zipcode {} ", zipcode);
            throw new RequestException("Invalid date", HttpStatus.NOT_FOUND);
        }

        return optionalAddress.stream()
                .map(addressMapper::toDto)
                .findFirst().get();
    }

    private boolean checkZipcode(String zipcode){
        String regexZipcode = "^\\d{5}(?:[-\\s]\\d{4})?$";
        return zipcode.matches(regexZipcode);
    }
}
