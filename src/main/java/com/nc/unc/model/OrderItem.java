package com.nc.unc.model;

public class OrderItem extends BaseEntity<Long> {
    private Product product;
    private int count;

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
    public String toString() {
        return "OrderItem{" +
                "product=" + product +
                ", count=" + count +
                '}';
    }
}
