package com.nc.unc.model;

import java.time.LocalDate;
import java.util.List;

public class Order extends BaseEntity<Long> {
    private final Customer customer;
    private final LocalDate createdWhen;
    private LocalDate sentWhen;
    private final double sum;
    private final List<OrderItem> products;

    private static final Status DEFAULT_STATUS = Status.CREATED;
    private Status curStatus;
    private boolean imposed;
    private final Address recipient;
    private final Address sender;

    public Order(long key,
                 Customer customer,
                 LocalDate createdWhen,
                 double sum,
                 List<OrderItem> products,
                 Address recipient,
                 Address sender) {
        super(key);
        this.curStatus = DEFAULT_STATUS;
        this.customer = customer;
        this.createdWhen = createdWhen;
        this.sum = sum;
        this.products = products;
        this.recipient = recipient;
        this.sender = sender;
    }

    public void setCurStatus(Status curStatus) { this.curStatus = curStatus; }

    public void setSentWhen(LocalDate sentWhen) { this.sentWhen = sentWhen; }

    public Address getRecipient() { return this.recipient; }

    public Customer getCustomer() { return this.customer; }

    public List<OrderItem> getProducts() { return this.products; }

    public double getSum() { return this.sum; }

    public Address getSender() { return this.sender; }

    public LocalDate getCreatedWhen() { return this.createdWhen; }

    public LocalDate getSentWhen() { return this.sentWhen; }

    public Status getCurStatus() { return this.curStatus; }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", createdWhen=" + createdWhen +
                ", sentWhen=" + sentWhen +
                ", sum=" + sum +
                ", products=" + products +
                ", curStatus=" + curStatus +
                ", imposed=" + imposed +
                ", recipient=" + recipient +
                ", sender=" + sender +
                '}';
    }
}
