package com.nc.unc;

import com.nc.unc.dao.*;
import com.nc.unc.dao.impl.*;
import com.nc.unc.service.*;
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
    public static OrderService orderService;
    public static StorageService storageService;
    public static CustomerService customerService;
    public static StoreService storeService;
    public static AddressService addressService;
    public static ProductHistoryService productHistoryService;

    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        AddressDao addressDao = new AddressDaoImpl();
        CustomerDao customerDao = new CustomerDaoImpl();
        OrderDao orderDao = new OrderDaoImpl();
        ProductDao productDao = new ProductDaoImpl();
        ProductHistoryDao productHistoryDao= new ProductHistoryDaoImpl();

        productHistoryService = new ProductHistoryServiceImpl(productHistoryDao);
        addressService = new AddressServiceImpl(addressDao);
        customerService = new CustomerServiceImpl(customerDao);
        storeService = new StoreServiceImpl(productDao, productHistoryService);
        orderService = new OrderServiceImpl(orderDao, storeService, new AddressServiceImpl(addressDao), customerService);

        Parent root = FXMLLoader.load(App.class.getResource("form/CreateOrder.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
