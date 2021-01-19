package com.nc.unc;

import com.nc.unc.dao.impl.AddressDaoImpl;
import com.nc.unc.dao.impl.CustomerDaoImpl;
import com.nc.unc.dao.impl.OrderDaoImpl;
import com.nc.unc.dao.impl.ProductDaoImpl;
import com.nc.unc.service.CustomerService;
import com.nc.unc.service.OrderService;
import com.nc.unc.service.StorageService;
import com.nc.unc.service.StoreService;
import com.nc.unc.service.impl.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Hello world!
 *
 */
public class App extends Application
{
    static public OrderService orderService;
    static public StorageService storageService;
    static public CustomerService customerService;
    static public StoreService storeService;


    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        AddressDaoImpl addressDao = new AddressDaoImpl();
        CustomerDaoImpl customerDao = new CustomerDaoImpl();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        ProductDaoImpl productDao = new ProductDaoImpl();


        customerService = new CustomerServiceImpl(customerDao);
        storeService = new StoreServiceImpl(productDao);
        orderService = new OrderServiceImpl(orderDao, storeService, new AddressServiceImpl(addressDao), customerService);

        Parent root = FXMLLoader.load(App.class.getResource("form/CreateOrder.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
