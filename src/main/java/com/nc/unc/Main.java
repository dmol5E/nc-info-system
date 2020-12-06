package com.nc.unc;

import com.nc.unc.model.Address;
import com.nc.unc.model.Customer;
import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;
import com.nc.unc.repositories.AddressRepository;
import com.nc.unc.repositories.CustomerRepository;
import com.nc.unc.repositories.OrderItemRepository;
import com.nc.unc.repositories.ProductRepository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Month;

public class Main  {
    private static CustomerRepository customerRepository = new CustomerRepository("src/main/resources/Customers.ser");
    private static AddressRepository addressRepository = new AddressRepository("src/main/resources/Address.ser");
    private static ProductRepository productRepository = new ProductRepository("src/main/resources/Product.ser");
    private static OrderItemRepository orderItemRepository = new OrderItemRepository("src/main/resources/OrderItem.ser");

    private static void addCust(){
        customerRepository.put(new Customer(1L,"firstName", "lastName", "PhoneNumber", LocalDate.of(2017, Month.NOVEMBER,30)));
        customerRepository.put(new Customer(2L,"firstName", "lastName", "PhoneNumber", LocalDate.of(1997, Month.APRIL, 2)));
        customerRepository.put(new Customer(3L,"firstName", "lastName", "PhoneNumber", LocalDate.of(1995, Month.APRIL, 2)));
        customerRepository.put(new Customer(4L,"firstName", "lastName", "PhoneNumber", LocalDate.of(1995, Month.APRIL, 2)));
        System.out.println(customerRepository.getAll().toString());
        System.out.println(customerRepository.sizeEntities());
        customerRepository.serialize();
    }

    private static void addAdd(){
        addressRepository.put(new Address(1L,"Address1", 1231));
        addressRepository.put(new Address(2L,"Address2", 1232));
        addressRepository.put(new Address(3L,"Address3", 1233));
        addressRepository.serialize();
    }

    private static void addPr(){
        productRepository.put(new Product(1L,"Name1", 200.0));
        productRepository.put(new Product(2L,"Name2", 200.0));
        productRepository.put(new Product(3L,"Name3", 200.0));
        productRepository.put(new Product(4L,"Name4", 200.0));
        productRepository.increaseCount(1, 10);
        productRepository.serialize();
    }

    private static void addItRep(){
        orderItemRepository.put(new OrderItem(1L, productRepository.getByKey(1L), 10));
        orderItemRepository.put(new OrderItem(2L, productRepository.getByKey(2L), 11));
        orderItemRepository.put(new OrderItem(3L, productRepository.getByKey(3L), 1));
    }

    public static void main(String[] args) throws IOException {
        addCust();
        addAdd();
        addPr();
        addItRep();
    }
}
