package com.nc.unc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class OrderItemDto {
    private int id;
    private String name;
    private float price;
    private int count;
    private int productHistoryId;
    private int orderId;
}
