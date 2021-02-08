package com.nc.unc.myService.mapper;

import com.nc.unc.dto.CustomerDto;
import com.nc.unc.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerDto dto) {
        return Objects.isNull(dto) ? null :
                Customer.builder()
                        .id(dto.getId())
                        .data(dto.getData())
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .phoneNumber(dto.getPhoneNumber())
                        .build();
    }

    public CustomerDto toDto(Customer entity){
        return Objects.isNull(entity) ? null :
                CustomerDto.builder()
                        .id(entity.getId())
                        .data(entity.getData())
                        .firstName(entity.getFirstName())
                        .lastName(entity.getLastName())
                        .phoneNumber(entity.getPhoneNumber())
                        .build();
    }

}
