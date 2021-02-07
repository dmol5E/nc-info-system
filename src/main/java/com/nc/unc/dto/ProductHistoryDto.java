package com.nc.unc.dto;

import com.nc.unc.model.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class ProductHistoryDto {
    private int id;
    private String name;
    private int price;
    private List<OrderItem> list;
}
