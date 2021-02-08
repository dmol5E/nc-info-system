package com.nc.unc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nc.unc.enums.StatusOrder;
import com.nc.unc.model.Address;
import com.nc.unc.model.Customer;
import com.nc.unc.model.OrderItem;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class OrderDto {
    private int id;

    @JsonProperty("createdWhen")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdWhen;

    @JsonProperty("sentWhen")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sentWhen;

    @JsonProperty("sum")
    private float sum;

    @JsonProperty("curStatusOrder")
    private StatusOrder curStatusOrder;

    @JsonProperty("fkCustomer")
    private int fkCustomer;

    @JsonProperty("fkRecipient")
    private int fkRecipient;

    @JsonProperty("fkSender")
    private int fkSender;

    @JsonProperty("customer")
    private Customer customer;

    @JsonProperty("recipient")
    private Address recipient;

    @JsonProperty("sender")
    private Address sender;

    @JsonProperty("products")
    private List<OrderItem> products;
}
