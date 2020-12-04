package com.nc.unc.service;

import com.nc.unc.model.Customer;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Deserialize implements Serializable {

    @SuppressWarnings("unchecked")
    public static List<Customer> deSerialize(){
        List<Customer> customers = new ArrayList<>();
        try(ObjectInputStream in = new ObjectInputStream
                (new FileInputStream("resources/Customers"))) {
            customers = (List<Customer>) in.readObject();
        }catch (Exception ex){
            ex.fillInStackTrace();
        }
        return customers;
    }

}
