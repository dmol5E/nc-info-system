package com.nc.unc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nc.unc.model.OrderItem;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class ProductHistoryDto {
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private float price;

    @JsonProperty("orderItems")
    private List<OrderItem> orderItems;
}
