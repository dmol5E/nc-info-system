package com.nc.unc.model;

import java.beans.ConstructorProperties;

public class Product extends BaseEntity<Long> {
    private String name;
    private double price;
    private int count;
    @ConstructorProperties({"key", "count", "name", "price"})
    public Product(long key,
                   int count,
                   String name,
                   double price) {
        super(key);
        this.count = count;
        this.name = name;
        this.price = price;
    }

    public int getCount() { return this.count; }

    public double getPrice() { return this.price; }

    public String getName() { return this.name; }

    public Product setCount(int count) { this.count = count; return this;}

    public Product setName(String name) { this.name = name; return this;}

    public Product setPrice(double price) { this.price = price; return this;}

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
