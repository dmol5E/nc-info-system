package com.nc.unc;


import com.nc.unc.dao.OrderItemDao;
import com.nc.unc.dao.ProductHistoryDao;
import com.nc.unc.dao.impl.*;
import com.nc.unc.enums.StatusOrder;
import com.nc.unc.model.*;
import com.nc.unc.util.json.JsonHelper;

import java.time.LocalDate;

public class Main  {

    public static void main(String[] args) {
        AddressDaoImpl addressDao = new AddressDaoImpl();
        //addressDao.update(new Address(1, "addressg",23321), 1);
        //addressDao.insert(Address.builder().address("Test1").zipCode(100).build());
        //addressDao.insert(Address.builder().address("Test2").zipCode(101).build());
        //addressDao.insert(Address.builder().address("Test3").zipCode(102).build());
        //addressDao.insert(Address.builder().address("Test4").zipCode(103).build());
        System.out.println(addressDao.getAll());
        //System.out.println(addressDao.searchByZipCode(Integer.parseInt("100")));

        CustomerDaoImpl customerDao = new CustomerDaoImpl();
        //customerDao.insert(Customer.builder().firstName("fTest1").lastName("lTest1").phoneNumber("pTest1").data(LocalDate.now()).build());
        //customerDao.insert(Customer.builder().firstName("fTest2").lastName("lTest2").phoneNumber("pTest2").data(LocalDate.now()).build());
        //customerDao.insert(Customer.builder().firstName("fTest3").lastName("lTest3").phoneNumber("pTest3").data(LocalDate.now()).build());
        //customerDao.insert(Customer.builder().firstName("fTest4").lastName("lTest4").phoneNumber("pTest4").data(LocalDate.now()).build());
        //customerDao.insert(Customer.builder().firstName("fTest5").lastName("lTest5").phoneNumber("pTest5").data(LocalDate.now()).build());
        //customerDao.update(new Customer(1, "updateCustomer", "updateCustomer", "updatePhone",LocalDate.now()), 1);
        System.out.println(customerDao.getAll());

        ProductDaoImpl productDao = new ProductDaoImpl();

        //productDao.update(new Product(1, 10, "updateProduct", 40.f),1);
        //productDao.insert(Product.builder().name("nTest1").count(10).price(100f).build());
        //productDao.insert(Product.builder().name("nTest2").price(100f).build());
        //productDao.insert(Product.builder().name("nTest3").count(5).price(1100f).build());
        //productDao.insert(Product.builder().name("nTest4").count(8).price(110f).build());
        //productDao.insert(Product.builder().name("nTest5").count(2).price(150f).build());
        //productDao.insert(Product.builder().name("nTest6").count(6).price(200f).build());

        System.out.println(productDao.getAll());

        ProductHistoryDao productHistoryDao = new ProductHistoryDaoImpl();

        //productHistoryDao.insert(ProductHistory.builder().name("nTest1").price(100f).build());
        //productHistoryDao.insert(ProductHistory.builder().name("nTest2").price(100f).build());
        //productHistoryDao.insert(ProductHistory.builder().name("nTest3").price(1100f).build());
        //productHistoryDao.insert(ProductHistory.builder().name("nTest4").price(110f).build());
        //productHistoryDao.insert(ProductHistory.builder().name("nTest5").price(150f).build());
        //productHistoryDao.insert(ProductHistory.builder().name("nTest6").price(200f).build());
        //System.out.println(productHistoryDao.getAll());

        OrderDaoImpl orderDao = new OrderDaoImpl();
        //orderDao.insert(Order.builder().sum(100)
        //        .createdWhen(LocalDate.now())
        //        .sender(addressDao.getByKey(1).get())
        //        .recipient(addressDao.getByKey(2).get())
        //        .customer(customerDao.getByKey(1).get())
        //        .build());

        //orderDao.insert(Order.builder().sum(120)
        //        .createdWhen(LocalDate.now())
        //        .sender(addressDao.getByKey(1).get())
        //        .recipient(addressDao.getByKey(3).get())
        //        .customer(customerDao.getByKey(2).get())
        //        .build());
        System.out.println(orderDao.getAll());

        OrderItemDao orderItemDao = new OrderItemDaoImpl();
        //System.out.println(orderItemDao.getByOrderId(1));

        //orderItemDao.insert(OrderItem.builder().name("nTest1").price(100).count(1).build(), 1, 1);
        //orderItemDao.insert(OrderItem.builder().name("nTest2").price(100).count(1).build(), 2, 1);
        //orderItemDao.insert(OrderItem.builder().name("nTest3").price(1100).count(1).build(), 3, 1);
        //orderItemDao.insert(OrderItem.builder().name("nTest4").price(110).count(1).build(), 4, 1);

        //orderItemDao.insert(OrderItem.builder().name("nTest4").price(100).count(1).build(), 4, 2);
        //orderItemDao.insert(OrderItem.builder().name("nTest5").price(150).count(1).build(), 5, 2);
        //orderItemDao.insert(OrderItem.builder().name("nTest6").price(200).count(1).build(), 6, 2);
        //orderItemDao.insert(OrderItem.builder().name("nTest1").price(100).count(1).build(), 1, 2);
        System.out.println(orderItemDao.getAll());
    }
}
