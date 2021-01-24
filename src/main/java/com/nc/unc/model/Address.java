package com.nc.unc.model;

import lombok.*;

import java.beans.ConstructorProperties;

@EqualsAndHashCode(callSuper=true)
@Setter
@Getter
@NoArgsConstructor
public class Address extends BaseEntity<Integer> {

    private String address;

    private int zipCode;

    @Builder(toBuilder = true)
    @ConstructorProperties({"key", "address", "zipcode"})
    public Address(int key, String address, int zipCode) {
        super(key);
        this.address = address;
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address\n{" +
                "\n  address='" + address + '\'' +
                "\n  zipCode=" + zipCode +
                "\n};";
    }
}
