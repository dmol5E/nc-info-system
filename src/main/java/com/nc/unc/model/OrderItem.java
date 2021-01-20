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
    private Product product;
    private int count;

    @Builder(toBuilder = true)
    @ConstructorProperties({"key", "product", "count"})
    public OrderItem(int key,
                     Product product,
                     int count) {
        super(key);
        this.product = product;
        this.count = count;
    }

    public float getPrice() { return product.getPrice(); }

    public String getName(){ return product.getName(); }

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
        return count == orderItem.count &&
                Objects.equals(product, orderItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, count);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "\n  key=" + super.key +
                "\n  product=" + product +
                "\n  count=" + count +
                '}';
    }
}
