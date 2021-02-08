package com.nc.unc.myService.mapper;

import com.nc.unc.dto.OrderDto;
import com.nc.unc.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderMapper {

    public Order toEntity(OrderDto orderDto){
        return Objects.isNull(orderDto) ? null :
                Order.builder()
                        .id(orderDto.getId())
                        .curStatusOrder(orderDto.getCurStatusOrder())
                        .sentWhen(orderDto.getSentWhen())
                        .sum(orderDto.getSum())
                        .createdWhen(orderDto.getCreatedWhen())
                        .sender(orderDto.getSender())
                        .recipient(orderDto.getRecipient())
                        .customer(orderDto.getCustomer())
                        .products(orderDto.getProducts())
                        .fkCustomer(orderDto.getFkCustomer())
                        .fkRecipient(orderDto.getFkRecipient())
                        .fkSender(orderDto.getFkSender())
                        .build();
    }

    public OrderDto toDto(Order order){
        return Objects.isNull(order) ? null :
                OrderDto.builder()
                        .id(order.getId())
                        .createdWhen(order.getCreatedWhen())
                        .curStatusOrder(order.getCurStatusOrder())
                        .customer(order.getCustomer())
                        .fkCustomer(order.getFkCustomer())
                        .fkRecipient(order.getFkRecipient())
                        .fkSender(order.getFkSender())
                        .products(order.getProducts())
                        .recipient(order.getRecipient())
                        .sender(order.getSender())
                        .sentWhen(order.getSentWhen())
                        .sum(order.getSum())
                        .build();
    }
}
