package com.nc.unc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nc.unc.model.enums.StatusOrder;
import com.nc.unc.dao.annotation.*;
import com.nc.unc.dao.annotation.enums.EnumType;
import client.util.json.LocalDateDeserializer;
import client.util.json.LocalDateSerializer;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "order", schema = "store")
@Builder(toBuilder = true)
public class Order {

    @PrimaryKey("id")
    private int id;

    @Attribute("customer")
    private int fkCustomer;

    @Attribute("created_when")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate createdWhen;

    @Attribute("sent_when")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate sentWhen;

    @Attribute("sum")
    private float sum;

    @Attribute("status")
    @Enumerated(value = EnumType.STRING)
    private StatusOrder curStatusOrder;

    @Attribute("recipient")
    private int fkRecipient;

    @Attribute("sender")
    private int fkSender;

    private Customer customer;

    private Address recipient;

    private Address sender;

    private List<OrderItem> products;

    private static final StatusOrder DEFAULT_STATUS = StatusOrder.CREATED;


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
