package com.nc.unc.model;


import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Product extends BaseEntity<Long> {
    private String name;
    private double price;
    private int count;
    private CheckBox select;

    public Product(long key,
                   String name,
                   double price) {
        super(key);
        this.count = 0;
        this.name = name;
        this.price = price;
        this.select=new CheckBox();
    }

    @Override
    public Long getKey() { return super.getKey(); }

    public int getCount() { return this.count; }

    public double getPrice() { return this.price; }

    public String getName() { return this.name; }

    public void setCount(int count) { this.count = count; }

    public void setName(String name) { this.name = name; }

    public void setPrice(double price) { this.price = price; }

    public CheckBox getSelect(){ return this.select; }

    public void setSelect(CheckBox select){ this.select=select; }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
