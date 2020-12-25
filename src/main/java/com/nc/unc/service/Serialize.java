package com.nc.unc.service;

import com.nc.unc.model.Customer;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class Serialize implements Serializable {

    public static void customersSerializable(List<Customer> customers){
        try(ObjectOutputStream out = new ObjectOutputStream
                (new FileOutputStream("resources/Customers"))){
            out.writeObject(customers);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

}
