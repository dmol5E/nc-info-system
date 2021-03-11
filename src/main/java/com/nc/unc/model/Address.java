package com.nc.unc.model;

import com.nc.unc.dao.annotation.Attribute;
import com.nc.unc.dao.annotation.PrimaryKey;
import com.nc.unc.dao.annotation.Table;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "address", schema = "store")
@Builder(toBuilder = true)
public class Address {

    @PrimaryKey("id")
    private int id;

    @Attribute("address")
    private String address;

    @Attribute("zipcode")
    private String zipCode;


    @Override
    public String toString() {
        return "Address\n{" +
                "\n  address='" + address + '\'' +
                "\n  zipCode=" + zipCode +
                "\n};";
    }
}
