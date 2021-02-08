package com.nc.unc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class AddressDto {
    private int id;
    @JsonProperty("address")
    private String address;
    @JsonProperty("zipcode")
    private int zipcode;

}