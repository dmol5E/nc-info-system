package com.nc.unc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nc.unc.enums.StatusOrder;
import com.nc.unc.util.json.LocalDateDeserializer;
import com.nc.unc.util.json.LocalDateSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseEntity<Integer> {
    private Customer customer;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate createdWhen;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate sentWhen;
    private double sum;

    private List<OrderItem> products;

    private static final StatusOrder DEFAULT_STATUS = StatusOrder.CREATED;
    private StatusOrder curStatusOrder;

    private Address recipient;
    private Address sender;

    @Builder(toBuilder = true)
    public Order(int key,
                 Customer customer,
                 LocalDate createdWhen,
                 LocalDate sentWhen,
                 double sum,
                 List<OrderItem> products,
                 Address recipient,
                 Address sender,
                 StatusOrder curStatusOrder) {
        super(key);
        this.curStatusOrder = DEFAULT_STATUS;
        this.customer = customer;
        this.createdWhen = createdWhen;
        this.sentWhen = sentWhen;
        this.sum = sum;
        this.products = products;
        this.recipient = recipient;
        this.sender = sender;
        this.curStatusOrder = curStatusOrder;
    }


    public Order(int key,
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
    public Order(@JsonProperty("key") int key,
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

    @Override
    public String toString() {
        return "Order{" +
                "\n  customer=" + customer +
                "\n  createdWhen=" + createdWhen +
                "\n  sentWhen=" + sentWhen +
                "\n  sum=" + sum +
                "\n  products=" + products +
                "\n  curStatus=" + curStatusOrder +
                "\n  recipient=" + recipient +
                "\n  sender=" + sender +
                "\n}";
    }
}
