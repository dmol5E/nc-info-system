package com.nc.unc.myService.mapper;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.model.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderItemMapper {

    public OrderItemDto toDto(OrderItem orderItem) {
        return Objects.isNull(orderItem) ? null :
                OrderItemDto.builder()
                        .id(orderItem.getId())
                        .count(orderItem.getCount())
                        .name(orderItem.getName())
                        .orderId(orderItem.getOrderId())
                        .productHistoryId(orderItem.getProductHistoryId())
                        .price(orderItem.getPrice())
                        .build();
    }

    public OrderItem toEntity(OrderItemDto orderItemDto) {
        return Objects.isNull(orderItemDto) ? null :
                OrderItem.builder()
                        .id(orderItemDto.getId())
                        .price(orderItemDto.getPrice())
                        .count(orderItemDto.getCount())
                        .name(orderItemDto.getName())
                        .orderId(orderItemDto.getOrderId())
                        .productHistoryId(orderItemDto.getProductHistoryId())
                        .build();
    }
}