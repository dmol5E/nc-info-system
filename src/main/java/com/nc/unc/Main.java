package com.nc.unc;


import com.nc.unc.dao.impl.AddressDaoImpl;
import com.nc.unc.dao.impl.CustomerDaoImpl;
import com.nc.unc.dao.impl.OrderDaoImpl;
import com.nc.unc.dao.impl.ProductDaoImpl;
import com.nc.unc.enums.StatusOrder;
import com.nc.unc.model.*;
import com.nc.unc.util.json.JsonHelper;

public class Main  {

    public static void main(String[] args) {
        AddressDaoImpl addressDao = new AddressDaoImpl();
        //addressDao.update(new Address(1, "addressg",23321), 1);
        System.out.println(addressDao.getAll());
        addressDao.insert(Address.builder().address("adsdda3s4").zipCode(111).build());

        CustomerDaoImpl customerDao = new CustomerDaoImpl();
        //customerDao.update(new Customer(1, "updateCustomer", "updateCustomer", "updatePhone",LocalDate.now()), 1);
        System.out.println(customerDao.getAll());
        //customerDao.insert("fistName", "lastName", "phone", LocalDate.now());
        //customerDao.insert("fistName", "lastName", "phone", LocalDate.now());

        ProductDaoImpl productDao = new ProductDaoImpl();
        //productDao.update(new Product(1, 10, "updateProduct", 40.f),1);
        System.out.println(productDao.getAll());
        //productDao.insert("product2", 150.f, 4);
        //productDao.insert("product1", 100.f, 4);


        OrderDaoImpl orderDao = new OrderDaoImpl();
        //List<OrderItem> list = new ArrayList<>();
        //list.add(new OrderItem(1, new Product(1, 76,"product1", 300.f),3));
        //list.add(new OrderItem(2, new Product(1, 21,"product2", 100.f),3));
        //list.add(new OrderItem(3, new Product(1, 52,"product3", 100.f),3));
        //String storage = JsonHelper.toJson(list);
        //orderDao.insert(1,1,2, storage, LocalDate.now(), 100.f);
        System.out.println(JsonHelper.toJson(orderDao.getAll()));
        System.out.println("-------------------------");
        System.out.println(productDao.getByKey(1).orElse(null));
        System.out.println(customerDao.getByKey(1).orElse(null));
        System.out.println(addressDao.getByKey(1).orElse(null));
        System.out.println(orderDao.getByKey(1).orElse(null));
        System.out.println("-------------------------");
        Order order = orderDao.getAll().get(1);
        order.setCurStatusOrder(StatusOrder.CREATED);
        orderDao.update(order,order.getKey());
    }
}
