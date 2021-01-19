package com.nc.unc;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.nc.unc.dao.DBConnector;
import com.nc.unc.dao.impl.AddressDaoImpl;
import com.nc.unc.dao.impl.CustomerDaoImpl;
import com.nc.unc.dao.impl.OrderDaoImpl;
import com.nc.unc.dao.impl.ProductDaoImpl;
import com.nc.unc.enums.StatusOrder;
import com.nc.unc.model.*;
import com.nc.unc.path.PathResources;
import com.nc.unc.repositories.Repository;
import com.nc.unc.repositories.impl.*;
import com.nc.unc.service.CustomerService;
import com.nc.unc.service.impl.CustomerServiceImpl;
import com.nc.unc.util.json.JsonHelper;
import com.nc.unc.util.serialize.impl.DataSourceImpl;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.Provider;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main  {
    private static CustomerRepository customerRepository = new CustomerRepository();
    private static AddressRepository addressRepository = new AddressRepository();
    private static ProductRepository productRepository = new ProductRepository();
    private static OrderItemRepository orderItemRepository = new OrderItemRepository();
    private static OrderRepository orderRepository = new OrderRepository();


    private static void addCust(){
        customerRepository.put(new Customer(0,"firstName", "lastName", "PhoneNumber", LocalDate.of(2017, Month.NOVEMBER,30)));
        customerRepository.put(new Customer(1,"firstName", "lastName", "PhoneNumber", LocalDate.of(1997, Month.APRIL, 2)));
        customerRepository.put(new Customer(2,"firstName", "lastName", "PhoneNumber", LocalDate.of(1995, Month.APRIL, 2)));
        customerRepository.put(new Customer(3,"firstName", "lastName", "PhoneNumber", LocalDate.of(1995, Month.APRIL, 2)));
    }

    private static void addAdd(){
        addressRepository.put(new Address(0,"Address1", 1231));
        addressRepository.put(new Address(1,"Address2", 1232));
        addressRepository.put(new Address(2,"Address3", 1233));
    }

    private static void addPr(){
        productRepository.put(new Product(0,0,"Name1", 200.0));
        productRepository.put(new Product(0,0,"Name1", 200.0));
        productRepository.put(new Product(1,0,"Name2", 200.0));
        productRepository.put(new Product(2,0,"Name3", 200.0));
        productRepository.put(new Product(3,0,"Name4", 200.0));
    }

    private static void addItRep(){
        orderItemRepository.put(new OrderItem(0, productRepository.getByKey(1), 10));
        orderItemRepository.put(new OrderItem(1, productRepository.getByKey(2), 11));
        orderItemRepository.put(new OrderItem(2, productRepository.getByKey(3), 1));
    }

    private static void addOrRep() {
        orderRepository.put(new Order(0,customerRepository.getByKey(0),LocalDate.of(2017, Month.NOVEMBER,30),
                4000.0,Stream.of(orderItemRepository.getByKey(0),orderItemRepository.getByKey(1)).collect(Collectors.toList()),
                addressRepository.getByKey(0), addressRepository.getByKey(1)));

        orderRepository.put(new Order(1,customerRepository.getByKey(1),LocalDate.of(2020, Month.NOVEMBER,30),
                1000.0,Stream.of(orderItemRepository.getByKey(0),orderItemRepository.getByKey(1)).collect(Collectors.toList()),
                addressRepository.getByKey(1), addressRepository.getByKey(2)));
    }



    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {//addCust();
       //addAdd();
       //addPr();
       //addItRep();
       //addOrRep();
       //List<RepositoryEntity<Long, ? extends BaseEntity<Long>>> repositories = new ArrayList<>();
       //repositories.add(addressRepository);
       //repositories.add(orderRepository);
       //repositories.add(productRepository);
       //repositories.add(customerRepository);
       //DataSourceImpl<RepositoryEntity<Long, ? extends BaseEntity<Long>>> dataSource = new DataSourceImpl<RepositoryEntity<Long, ? extends BaseEntity<Long>>>(repositories);
       //dataSource.serialize();
       //String json = JsonHelper.toJson(repositories);
       //List<RepositoryEntity<Long, ? extends BaseEntity<Long>>> repos = dataSource.deSerialize(new TypeReference<List<RepositoryEntity<Long,? extends BaseEntity<Long>>>>() {});


       //CustomerRepository c = (CustomerRepository) dataSource.search(CustomerRepository.class);
       //CustomerService customerService = new CustomerServiceImpl(c);
       //customerService.putCustomer("test","test", "test",LocalDate.now());
       //System.out.println(customerService.getAll());
       //System.out.println(repos.toString());

        //AddressDaoImpl addressDao = new AddressDaoImpl();
        //addressDao.insert("address", 123);
        //addressDao.insert("address1", 134);

        //CustomerDaoImpl customerDao = new CustomerDaoImpl();
        //customerDao.insert("fistName", "lastName", "phone", LocalDate.now());
        //customerDao.insert("fistName", "lastName", "phone", LocalDate.now());

        //ProductDaoImpl productDao = new ProductDaoImpl();
        //productDao.insert("name", 100.f, 4);


        OrderDaoImpl orderDao = new OrderDaoImpl();

        List<OrderItem> list = new ArrayList<>();
        list.add(new OrderItem(1, new Product(1, 76,"product1", 300.f),3));
        list.add(new OrderItem(2, new Product(1, 21,"product2", 134.f),3));
        list.add(new OrderItem(3, new Product(1, 52,"product3", 128.f),3));
        String str = JsonHelper.toJson(list);
        orderDao.insert(1,1,2, str, LocalDate.now(), 100.f);
        System.out.println(orderDao.getAll());
    }
}
