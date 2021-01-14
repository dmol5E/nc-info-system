package com.nc.unc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nc.unc.enums.StatusOrder;
import com.nc.unc.util.json.LocalDateDeserializer;
import com.nc.unc.util.json.LocalDateSerializer;

import java.time.LocalDate;
import java.util.List;

public class Order extends BaseEntity<Long> {
    private final Customer customer;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private final LocalDate createdWhen;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate sentWhen = LocalDate.now();
    private final double sum;

    private final List<OrderItem> products;

    private static final StatusOrder DEFAULT_STATUS = StatusOrder.CREATED;
    private StatusOrder curStatusOrder;
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

    @JsonCreator
    public Order(@JsonProperty("key") long key,
                 @JsonProperty("customer") Customer customer,
                 @JsonProperty("createdWhen") LocalDate createdWhen,
                 @JsonProperty("sentWhen") LocalDate sentWhen,
                 @JsonProperty("sum") double sum,
                 @JsonProperty("products") List<OrderItem> products,
                 @JsonProperty("recipient") Address recipient,
                 @JsonProperty("sender") Address sender) {
        super(key);
        this.products = products;
        this.curStatusOrder = DEFAULT_STATUS;
        this.customer = customer;
        this.createdWhen = createdWhen;
        this.sentWhen = sentWhen;
        this.sum = sum;
        this.recipient = recipient;
        this.sender = sender;
    }

    @JsonIgnore
    public String getRecipientAddress(){ return this.recipient.getAddress(); }

    @JsonIgnore
    public String getSenderAddress(){ return this.sender.getAddress(); }

    @JsonIgnore
    public String getName(){ return this.customer.getFirstName(); }

    @JsonIgnore
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
                "\n customer=" + customer +
                "\n createdWhen=" + createdWhen +
                "\n sentWhen=" + sentWhen +
                "\n sum=" + sum +
                "\n products=" + products +
                "\n curStatus=" + curStatusOrder +
                "\n recipient=" + recipient +
                "\n sender=" + sender +
                "\n}";
    }
}
