package com.nc.unc.model;

import java.beans.ConstructorProperties;

public class Address extends BaseEntity<Long> {

    private String address;

    private int zipCode;

    @ConstructorProperties({"key", "address", "zipcode"})
    public Address(Long key, String address, int zipCode) {
        super(key);
        this.address = address;
        this.zipCode = zipCode;
    }

    public int getZipCode() { return this.zipCode; }

    public String getAddress() { return this.address; }

    public void setAddress(String address) { this.address = address; }

    public void setZipCode(int zipCode) { this.zipCode = zipCode; }

    @Override
    public String toString() {
        return "Address{" +
                "\n  address='" + address + '\'' +
                "\n  zipCode=" + zipCode +
                '}';
    }
}
