package com.nc.unc.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class OrderItem extends BaseEntity<Integer> {
    private String name;
    private float price;
    private int count;


    @Builder(toBuilder = true)
    @ConstructorProperties({"key", "name", "price", "count"})
    public OrderItem(int key,
                     String name,
                     float price,
                     int count) {
        super(key);
        this.name = name;
        this.price = price;
        this.count = count;
    }


    @Override
    @JsonGetter("id")
    public Integer getKey() {
        return super.getKey();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Float.compare(orderItem.price, price) == 0 &&
                count == orderItem.count &&
                Objects.equals(name, orderItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, count);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", key=" + key +
                '}';
    }
}
