package com.nc.unc.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nc.unc.util.json.LocalDateDeserializer;
import com.nc.unc.util.json.LocalDateSerializer;


import java.time.LocalDate;
import java.util.Objects;
import java.beans.ConstructorProperties;

public class Customer extends BaseEntity<Long> {
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate data;

    @ConstructorProperties({"id","firstName","lastName","phoneNumber","data"})
    public Customer (long id,
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
                "\n  id=" + super.key +
                "\n  firstName='" + this.firstName + '\'' +
                "\n  lastName='" + this.lastName + '\'' +
                "\n  phoneNumber='" + this.phoneNumber + '\'' +
                "\n  data=" + this.data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return  Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(phoneNumber, customer.phoneNumber) &&
                Objects.equals(data, customer.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber, data);
    }
}
