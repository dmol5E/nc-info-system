package com.nc.unc.myService.mapper;

import com.nc.unc.dto.AddressDto;
import com.nc.unc.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AddressMapper {
    private final ModelMapper mapper;

    public AddressMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Address toEntity(AddressDto addressDto) {
        return Objects.isNull(addressDto) ? null : mapper.map(addressDto, Address.class);
    }

    public AddressDto toDto(Address address) {
        return Objects.isNull(address) ? null : mapper.map(address, AddressDto.class);
    }
}
