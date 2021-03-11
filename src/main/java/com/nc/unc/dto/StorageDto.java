package com.nc.unc.dto;

import lombok.Data;

import java.util.List;

@Data
public class StorageDto {
    private List<OrderItemDto> orderItems;
}
