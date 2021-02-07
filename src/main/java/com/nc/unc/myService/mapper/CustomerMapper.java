package com.nc.unc.myService.mapper;

import com.nc.unc.dto.CustomerDto;
import com.nc.unc.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerMapper {
    private final ModelMapper mapper;

    @Autowired
    public CustomerMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Customer toEntity(CustomerDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Customer.class);
    }

    public CustomerDto toDto(Customer entity){
        return Objects.isNull(entity) ? null : mapper.map(entity, CustomerDto.class);
    }

}
