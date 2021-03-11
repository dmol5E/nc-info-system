package com.nc.unc.service.mapper.impl;

import com.nc.unc.dto.OrderDto;
import com.nc.unc.model.Order;
import com.nc.unc.dao.IOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderMapper {

    private IOrderDao orderDao;

    @Autowired
    public OrderMapper(IOrderDao orderDao){
        this.orderDao = orderDao;
    }

    public Order toEntity(OrderDto orderDto){
        return Objects.isNull(orderDto) ? null :
                orderDao.find(orderDto.getId()).orElseThrow();
    }

    public OrderDto toDto(Order order){
        return Objects.isNull(order) ? null :
                OrderDto.builder()
                        .id(order.getId())
                        .createdWhen(order.getCreatedWhen())
                        .curStatusOrder(order.getCurStatusOrder())
                        .sentWhen(order.getSentWhen())
                        .sum(order.getSum())
                        .firstName(order.getCustomer().getFirstName())
                        .lastName(order.getCustomer().getLastName())
                        .phoneNumber(order.getCustomer().getPhoneNumber())
                        .recipientAddress(order.getRecipient().getAddress())
                        .senderAddress(order.getSender().getAddress())
                        .build();
    }

}
