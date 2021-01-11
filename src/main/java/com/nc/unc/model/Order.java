package com.nc.unc.model;

import com.nc.unc.enums.StatusOrder;

import java.time.LocalDate;
import java.util.List;

public class Order extends BaseEntity<Long> {
    private final Customer customer;
    private final LocalDate createdWhen;
    private LocalDate sentWhen;
    private final double sum;
    private final List<OrderItem> products;

    private static final StatusOrder DEFAULT_STATUS = StatusOrder.CREATED;
    private StatusOrder curStatusOrder;
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
        this.curStatusOrder = DEFAULT_STATUS;
        this.customer = customer;
        this.createdWhen = createdWhen;
        this.sum = sum;
        this.products = products;
        this.recipient = recipient;
        this.sender = sender;
    }
    public String getRecipientAddress(){ return this.recipient.getAddress(); }

    public String getSenderAddress(){ return this.sender.getAddress(); }

    public String getName(){ return this.customer.getFirstName(); }

    public String getLastName(){ return this.customer.getLastName(); }

    public Order setCurStatus(StatusOrder curStatusOrder) { this.curStatusOrder = curStatusOrder; return this;}

    public Order setSentWhen(LocalDate sentWhen) { this.sentWhen = sentWhen; return this;}

    public Address getRecipient() { return this.recipient; }

    public Customer getCustomer() { return this.customer; }

    public List<OrderItem> getProducts() { return this.products; }

    public double getSum() { return this.sum; }

    public Address getSender() { return this.sender; }

    public LocalDate getCreatedWhen() { return this.createdWhen; }

    public LocalDate getSentWhen() { return this.sentWhen; }

    public StatusOrder getCurStatus() { return this.curStatusOrder; }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", createdWhen=" + createdWhen +
                ", sentWhen=" + sentWhen +
                ", sum=" + sum +
                ", products=" + products +
                ", curStatus=" + curStatusOrder +
                ", imposed=" + imposed +
                ", recipient=" + recipient +
                ", sender=" + sender +
                '}';
    }
}
