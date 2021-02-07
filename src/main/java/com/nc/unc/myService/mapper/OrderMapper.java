package com.nc.unc.myService.mapper;

import com.nc.unc.dto.OrderDto;
import com.nc.unc.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderMapper {

    private final ModelMapper mapper;

    public OrderMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Order toEntity(OrderDto orderDto){
        return Objects.isNull(orderDto) ? null : mapper.map(orderDto, Order.class);
    }

    public OrderDto toDto(Order order){
        return Objects.isNull(order) ? null : mapper.map(order, OrderDto.class);
    }
}
