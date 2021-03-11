package com.nc.unc.model;


import com.nc.unc.dao.annotation.Attribute;
import com.nc.unc.dao.annotation.PrimaryKey;
import com.nc.unc.dao.annotation.Table;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "product_history", schema = "store")
@Builder(toBuilder = true)
public class ProductHistory  {

    @PrimaryKey("id")
    private int id;
    @Attribute("name")
    private String name;
    @Attribute("price")
    private float price;
    private List<OrderItem> orderItems;

    @Override
    public String toString() {
        return "ProductHistory{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", orderItem=" + orderItems.toString() +
                '}';
    }
}
