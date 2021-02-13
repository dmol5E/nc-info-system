package com.nc.unc.myService.mapper.impl;

import com.nc.unc.dto.AddressDto;
import com.nc.unc.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AddressMapper {

    public Address toEntity(AddressDto addressDto) {
        return Objects.isNull(addressDto) ? null :
                Address.builder()
                        .id(addressDto.getId())
                        .zipCode(addressDto.getZipcode())
                        .address(addressDto.getAddress())
                        .build();
    }

    public AddressDto toDto(Address address) {
        return Objects.isNull(address) ? null :
                AddressDto.builder()
                        .id(address.getId())
                        .address(address.getAddress())
                        .zipcode(address.getZipCode())
                        .build();
    }
}
