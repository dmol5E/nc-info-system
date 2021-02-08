package com.nc.unc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class OrderItemDto {
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private float price;

    @JsonProperty("count")
    private int count;

    @JsonProperty("productHistoryId")
    private int productHistoryId;

    @JsonProperty("orderId")
    private int orderId;
}
