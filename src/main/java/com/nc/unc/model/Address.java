package com.nc.unc.model;

public class Address extends BaseEntity<Long> {

    private String address;

    private int zipCode;

    public Address(Long key, String address, int zipCode) {
        super(key);
        this.address = address;
        this.zipCode = zipCode;
    }

    public int getZipCode() { return this.zipCode; }

    public String getAddress() { return this.address; }

    public void setAddress(String address) { this.address = address; }

    public void setZipCode(int zipCode) { this.zipCode = zipCode; }
}
