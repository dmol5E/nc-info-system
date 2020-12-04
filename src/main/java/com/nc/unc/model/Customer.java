package com.nc.unc.model;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable, Comparable<Customer> {
    private final long id;
    private String FIO;
    private String phoneNumber;
    private Date data;

    public Customer(long id,
                    String phoneNumber,
                    String FIO,
                    Date data)
    {
        this.id = id;
        this.data = data;
        this.FIO = FIO;
        this.phoneNumber = phoneNumber;

    }

    public void setFIO(String FIO) { this.FIO = FIO; }

    public void setData(Date data) { this.data = data; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public long getId() { return this.id; }

    public String getFIO() { return this.FIO; }

    public String getPhoneNumber() { return this.phoneNumber; }

    public Date getData() { return this.data; }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + this.id +
                ", FIO='" + this.FIO + '\'' +
                ", phoneNumber='" + this.phoneNumber + '\'' +
                ", data=" + this.data +
                '}';
    }

    @Override
    public int compareTo(Customer o) {
        return this.FIO.compareTo(o.FIO);
    }
}
