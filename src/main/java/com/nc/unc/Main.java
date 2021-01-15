package com.nc.unc;


import com.fasterxml.jackson.core.type.TypeReference;
import com.nc.unc.enums.StatusOrder;
import com.nc.unc.model.*;
import com.nc.unc.path.PathResources;
import com.nc.unc.repositories.Repository;
import com.nc.unc.repositories.impl.*;
import com.nc.unc.util.json.JsonHelper;
import com.nc.unc.util.serialize.impl.DataSourceImpl;


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
        customerRepository.put(new Customer(0L,"firstName", "lastName", "PhoneNumber", LocalDate.of(2017, Month.NOVEMBER,30)));
        customerRepository.put(new Customer(1L,"firstName", "lastName", "PhoneNumber", LocalDate.of(1997, Month.APRIL, 2)));
        customerRepository.put(new Customer(2L,"firstName", "lastName", "PhoneNumber", LocalDate.of(1995, Month.APRIL, 2)));
        customerRepository.put(new Customer(3L,"firstName", "lastName", "PhoneNumber", LocalDate.of(1995, Month.APRIL, 2)));
    }

    private static void addAdd(){
        addressRepository.put(new Address(0L,"Address1", 1231));
        addressRepository.put(new Address(1L,"Address2", 1232));
        addressRepository.put(new Address(2L,"Address3", 1233));
    }

    private static void addPr(){
        productRepository.put(new Product(0L,0,"Name1", 200.0));
        productRepository.put(new Product(0L,0,"Name1", 200.0));
        productRepository.put(new Product(1L,0,"Name2", 200.0));
        productRepository.put(new Product(2L,0,"Name3", 200.0));
        productRepository.put(new Product(3L,0,"Name4", 200.0));
    }

    private static void addItRep(){
        orderItemRepository.put(new OrderItem(0L, productRepository.getByKey(1L), 10));
        orderItemRepository.put(new OrderItem(1L, productRepository.getByKey(2L), 11));
        orderItemRepository.put(new OrderItem(2L, productRepository.getByKey(3L), 1));
    }

    private static void addOrRep() {
        orderRepository.put(new Order(0L,customerRepository.getByKey(0L),LocalDate.of(2017, Month.NOVEMBER,30),
                4000.0,Stream.of(orderItemRepository.getByKey(0L),orderItemRepository.getByKey(1L)).collect(Collectors.toList()),
                addressRepository.getByKey(0L), addressRepository.getByKey(1L)));

        orderRepository.put(new Order(1L,customerRepository.getByKey(1L),LocalDate.of(2020, Month.NOVEMBER,30),
                1000.0,Stream.of(orderItemRepository.getByKey(0L),orderItemRepository.getByKey(1L)).collect(Collectors.toList()),
                addressRepository.getByKey(1L), addressRepository.getByKey(2L)));
    }

    public static void main(String[] args) throws IOException {
        addCust();
        addAdd();
        addPr();
        addItRep();
        addOrRep();
        List<RepositoryEntity<Long, ? extends BaseEntity<Long>>> repositories = new ArrayList<>();
        repositories.add(addressRepository);
        repositories.add(orderRepository);
        repositories.add(productRepository);
        repositories.add(customerRepository);
        DataSourceImpl<RepositoryEntity<Long, ? extends BaseEntity<Long>>> dataSource = new DataSourceImpl<>(repositories);
        dataSource.serialize();
        String json = JsonHelper.toJson(repositories);
        String str = JsonHelper.fromJsonFile(PathResources.path, String.class);
        List<RepositoryEntity<Long, ? extends BaseEntity<Long>>> repos = dataSource.deSerialize(new TypeReference<>() {});
        System.out.println(dataSource.search(CustomerRepository.class).toString());

    }
}
