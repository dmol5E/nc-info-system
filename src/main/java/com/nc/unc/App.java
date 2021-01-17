package com.nc.unc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nc.unc.model.BaseEntity;
import com.nc.unc.repositories.Repository;
import com.nc.unc.repositories.impl.*;
import com.nc.unc.service.CustomerService;
import com.nc.unc.service.OrderService;
import com.nc.unc.service.StorageService;
import com.nc.unc.service.StoreService;
import com.nc.unc.service.impl.CustomerServiceImpl;
import com.nc.unc.service.impl.OrderServiceImpl;
import com.nc.unc.service.impl.StorageServiceImpl;
import com.nc.unc.service.impl.StoreServiceImpl;
import com.nc.unc.util.serialize.DataSource;
import com.nc.unc.util.serialize.impl.DataSourceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

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
        DataSource<RepositoryEntity<Long, ? extends BaseEntity<Long>>> dataSource = new DataSourceImpl<>();
        List<RepositoryEntity<Long, ? extends BaseEntity<Long>>> repos = dataSource.deSerialize(new TypeReference<>() {});

        customerService = new CustomerServiceImpl((CustomerRepository) dataSource.search(CustomerRepository.class));
        storeService = new StoreServiceImpl((ProductRepository) dataSource.search(ProductRepository.class));
        orderService = new OrderServiceImpl((OrderRepository) dataSource.search(OrderRepository.class),
                storeService,
                (AddressRepository) dataSource.search(AddressRepository.class), customerService,
                (OrderItemRepository) dataSource.search(OrderItemRepository.class));
        storageService = new StorageServiceImpl();


        Parent root = FXMLLoader.load(App.class.getResource("form/CreateOrder.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
