package com.nc.unc.model;


import com.nc.unc.myDao.annotation.Attribute;
import com.nc.unc.myDao.annotation.PrimaryKey;
import com.nc.unc.myDao.annotation.Table;
import lombok.*;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Map;

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
    private Map<Integer, OrderItem> orderItem;

    @Override
    public String toString() {
        return "ProductHistory{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", orderItem=" + orderItem.toString() +
                '}';
    }
}
