package com.nc.unc.model;

import java.time.LocalDate;

public class Customer extends BaseEntity<Long> {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate data;

    public Customer(long id,
                    String firstName,
                    String lastName,
                    String phoneNumber,
                    LocalDate data)
    {
        super(id);
        this.data = data;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setData(LocalDate data) { this.data = data; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getFirstName() { return this.firstName; }

    public String getLastName() { return this.lastName; }

    public String getPhoneNumber() { return this.phoneNumber; }

    public LocalDate getData() { return this.data; }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + super.key +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", phoneNumber='" + this.phoneNumber + '\'' +
                ", data=" + this.data +
                '}';
    }
}
