package com.nc.unc;

import com.nc.unc.json.SerialisePoolRepository;
import com.nc.unc.model.*;
import com.nc.unc.repositories.*;

import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main  {
    private static CustomerRepository customerRepository = new CustomerRepository();
    private static AddressRepository addressRepository = new AddressRepository();
    private static ProductRepository productRepository = new ProductRepository();
    private static OrderItemRepository orderItemRepository = new OrderItemRepository();
    private static OrderRepository orderRepository = new OrderRepository();

    private static void addCust(){
        customerRepository.put(new Customer(1L,"firstName", "lastName", "PhoneNumber", LocalDate.of(2017, Month.NOVEMBER,30)));
        customerRepository.put(new Customer(2L,"firstName", "lastName", "PhoneNumber", LocalDate.of(1997, Month.APRIL, 2)));
        customerRepository.put(new Customer(3L,"firstName", "lastName", "PhoneNumber", LocalDate.of(1995, Month.APRIL, 2)));
        customerRepository.put(new Customer(4L,"firstName", "lastName", "PhoneNumber", LocalDate.of(1995, Month.APRIL, 2)));
        System.out.println(customerRepository.sizeEntities());
    }

    private static void addAdd(){
        addressRepository.put(new Address(1L,"Address1", 1231));
        addressRepository.put(new Address(2L,"Address2", 1232));
        addressRepository.put(new Address(3L,"Address3", 1233));
    }

    private static void addPr(){
        productRepository.put(new Product(1L,"Name1", 200.0));
        productRepository.put(new Product(2L,"Name2", 200.0));
        productRepository.put(new Product(3L,"Name3", 200.0));
        productRepository.put(new Product(4L,"Name4", 200.0));
    }

    private static void addItRep(){
        orderItemRepository.put(new OrderItem(1L, productRepository.getByKey(1L), 10));
        orderItemRepository.put(new OrderItem(2L, productRepository.getByKey(2L), 11));
        orderItemRepository.put(new OrderItem(3L, productRepository.getByKey(3L), 1));
    }

    private static void addOrRep() {
        orderRepository.put(new Order(1L,customerRepository.getByKey(0L),LocalDate.of(2017, Month.NOVEMBER,30),
                4000.0,Stream.of(orderItemRepository.getByKey(0L),orderItemRepository.getByKey(1L)).collect(Collectors.toList()),
                addressRepository.getByKey(0L), addressRepository.getByKey(1L)));

        orderRepository.put(new Order(2L,customerRepository.getByKey(1L),LocalDate.of(2020, Month.NOVEMBER,30),
                1000.0,Stream.of(orderItemRepository.getByKey(0L),orderItemRepository.getByKey(1L)).collect(Collectors.toList()),
                addressRepository.getByKey(1L), addressRepository.getByKey(2L)));
    }

    public static void main(String[] args) throws IOException {
        addCust();
        addAdd();
        addPr();
        addItRep();
        Gson gson = new Gson();
        List<Repository<Long,? extends BaseEntity<Long>>> repositories
                = Stream.of(customerRepository, addressRepository, productRepository,orderItemRepository,orderRepository).collect(Collectors.toList());
        SerialisePoolRepository serialisePoolRepository = new SerialisePoolRepository(repositories);
        serialisePoolRepository.serialize();
        var result = serialisePoolRepository.deSerialize();
        for (var it : result){
            System.out.println(it.getEntities().toString());
        }
        repositories.add(customerRepository);
        Repository<Long, Address> repository = new AddressRepository();
        BaseEntity<Long> baseEntity = new Address(1L, "32", 32);
        String str = gson.toJson(customerRepository);
        CustomerRepository customerRepository2 = gson.fromJson(str ,CustomerRepository.class);
        System.out.println(customerRepository2.toString());
    }
}
