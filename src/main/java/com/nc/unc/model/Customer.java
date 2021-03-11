package com.nc.unc.model;

import com.nc.unc.dao.annotation.Attribute;
import com.nc.unc.dao.annotation.PrimaryKey;
import com.nc.unc.dao.annotation.Table;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.util.Objects;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(value = "customer", schema = "store")
public class Customer {

    @PrimaryKey("id")
    private int id;

    @Attribute("first_name")
    private String firstName;

    @Attribute("last_name")
    private String lastName;

    @Attribute("phone_number")
    private String phoneNumber;

    @Attribute("date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;


    @Override
    public String toString() {
        return "Customer{" +
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
