package com.nc.unc.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nc.unc.dao.annotation.Attribute;
import com.nc.unc.dao.annotation.PrimaryKey;
import com.nc.unc.dao.annotation.Table;
import lombok.*;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties({"count"})
@Table(value = "Product", schema = "store")
public class Product {

    @PrimaryKey("id")
    private int id;
    @Attribute("name")
    private String name;
    @Attribute("price")
    private float price;
    @Attribute("count")
    private int count;

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
                "\n  name='" + name + '\'' +
                "\n  price=" + price +
                "\n  count=" + count +
                '}';
    }
}
