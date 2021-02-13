package com.nc.unc.myService.mapper.impl;

import com.nc.unc.dto.ProductHistoryDto;
import com.nc.unc.model.ProductHistory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductHistoryMapper {

    public ProductHistory toEntity(ProductHistoryDto productHistoryDto){
        return Objects.isNull(productHistoryDto) ? null :
                ProductHistory.builder()
                        .id(productHistoryDto.getId())
                        .price(productHistoryDto.getPrice())
                        .name(productHistoryDto.getName())
                        .orderItems(productHistoryDto.getOrderItems())
                        .build();
    }

    public ProductHistoryDto toDto(ProductHistory productHistory){
        return Objects.isNull(productHistory) ? null :
                ProductHistoryDto.builder()
                        .id(productHistory.getId())
                        .name(productHistory.getName())
                        .orderItems(productHistory.getOrderItems())
                        .price(productHistory.getPrice())
                        .build();
    }
}
