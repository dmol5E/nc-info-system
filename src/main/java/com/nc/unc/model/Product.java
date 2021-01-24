package com.nc.unc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.util.Objects;


@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"count"})
public class Product extends BaseEntity<Integer> {
    private String name;
    private float price;
    private int count;
    @Builder
    @ConstructorProperties({"key", "count", "name", "price"})
    public Product(int key,
                   int count,
                   String name,
                   float price) {
        super(key);
        this.count = count;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Float.compare(product.price, price) == 0 &&
                count == product.count &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, count);
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
