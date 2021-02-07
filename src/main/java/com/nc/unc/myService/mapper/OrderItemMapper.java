package com.nc.unc.myService.mapper;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.model.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderItemMapper {

    private final ModelMapper mapper;

    public OrderItemMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public OrderItemDto toDto(OrderItem orderItem) {
        return Objects.isNull(orderItem) ? null : mapper.map(orderItem, OrderItemDto.class);
    }

    public OrderItem toEntity(OrderItemDto orderItemDto) {
        return Objects.isNull(orderItemDto) ? null : mapper.map(orderItemDto, OrderItem.class);
    }
}
