package com.nc.unc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;



@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"count"})
public class Product extends BaseEntity<Integer> {
    private String name;
    private double price;
    private int count;
    @Builder
    @ConstructorProperties({"key", "count", "name", "price"})
    public Product(int key,
                   int count,
                   String name,
                   double price) {
        super(key);
        this.count = count;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "\n  key=" + super.key +
                "\n  name='" + name + '\'' +
                "\n  price=" + price +
                "\n  count=" + count +
                '}';
    }
}
