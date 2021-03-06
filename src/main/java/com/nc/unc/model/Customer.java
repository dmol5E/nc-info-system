package com.nc.unc.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nc.unc.util.json.LocalDateDeserializer;
import com.nc.unc.util.json.LocalDateSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.Objects;
import java.beans.ConstructorProperties;


@Setter
@Getter
@NoArgsConstructor
public class Customer extends BaseEntity<Integer> {
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate data;

    @Builder(toBuilder = true)
    @ConstructorProperties({"id","firstName","lastName","phoneNumber","data"})
    public Customer (int key,
                    String firstName,
                    String lastName,
                    String phoneNumber,
                    LocalDate data)
    {
        super(key);
        this.data = data;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

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
