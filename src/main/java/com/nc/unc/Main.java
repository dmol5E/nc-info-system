package com.nc.unc;

import com.nc.unc.model.*;
import com.nc.unc.repositories.*;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static CustomerRepository customerRepository = new CustomerRepository();
    private static AddressRepository addressRepository = new AddressRepository();
    private static ProductRepository productRepository = new ProductRepository();
    private static OrderItemRepository orderItemRepository = new OrderItemRepository();
    private static OrderRepository orderRepository = new OrderRepository();


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

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/nc/unc/form/CreateOrder.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
        //addCust();
        addAdd();
        addPr();
        addItRep();
        Gson gson = new Gson();
        Repository<Long, Address> repository = new AddressRepository();

        //BaseEntity<Long> baseEntity = new Address(1L, "32", 32);
        //String str = gson.toJson(repository);
        //System.out.println(str);
        //System.out.print("G");

        repository.put(new Address(1L,"Address1", 1231));
        System.out.print(repository.getByKey(1L).getAddress());
        //System.out.print(repository.getByKey(2L).getAddress());
        //System.out.print(repository.getByKey(3L).getAddress());

    }
}
