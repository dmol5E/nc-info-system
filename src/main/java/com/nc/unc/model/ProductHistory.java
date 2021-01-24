package com.nc.unc.model;


import lombok.*;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class ProductHistory extends BaseEntity<Integer> {
    private String name;
    private float price;
    private Map<Integer, OrderItem> orderItem;

    @Builder(toBuilder = true)
    @ConstructorProperties({"key", "name", "price", "orderItem"})
    public ProductHistory(int key,
                          String name,
                          float price,
                          Map<Integer, OrderItem> orderItem) {
        super(key);
        this.name = name;
        this.price = price;
        this.orderItem = orderItem;
    }

    @Override
    public String toString() {
        return "ProductHistory{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", orderItem=" + orderItem.toString() +
                ", key=" + key +
                '}';
    }
}
