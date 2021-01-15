package com.nc.unc.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.beans.ConstructorProperties;

public class OrderItem extends BaseEntity<Long> {
    private Product product;
    private int count;

    @ConstructorProperties({"key", "product", "count"})
    public OrderItem(Long key,
                     Product product,
                     int count) {
        super(key);
        this.product = product;
        this.count = count;
    }

    public void setCount(int count) { this.count = count; }

    public void setProduct(Product product) { this.product = product; }

    public int getCount() { return this.count; }

    public Product getProduct() { return this.product; }

    @Override
    @JsonGetter("id")
    public Long getKey() {
        return super.getKey();
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
