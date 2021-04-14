package com.nc.unc.dto;

import com.nc.unc.client.util.json.LocalDateDeserializer;
import com.nc.unc.client.util.json.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nc.unc.model.enums.StatusOrder;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate createdWhen;

    @JsonProperty("sentWhen")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate sentWhen;

    @JsonProperty("sum")
    private float sum;

    @JsonProperty("curStatusOrder")
    private StatusOrder curStatusOrder;

    @JsonProperty("recipientAddress")
    private String recipientAddress;

    @JsonProperty("senderAddress")
    private String senderAddress;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("phoneNumber")
    private String phoneNumber;
}
