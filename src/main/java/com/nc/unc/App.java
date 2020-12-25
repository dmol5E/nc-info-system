package com.nc.unc;

import com.nc.unc.form.CreateOrder1;
import com.nc.unc.form.CreateOrder2;
import com.nc.unc.model.Customer;
import com.nc.unc.repositories.CustomerRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    private static List<String> list1 = Stream.of("3One","One Two","gf","321423fs","sda")
            .collect(Collectors.toList());
    public static List<String> getCustomer(String string) {
        return list1.stream()
                .filter((a) -> a.toLowerCase().contains(string.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static void main( String[] args ) throws Exception {
        //CreateOrder1 createOrder1=new CreateOrder1();
        //createOrder1.start(new Stage());
        String name1_1="Алексей",name1_2="Афанасьев",name2_1="Дмитрий",name2_2="Баканов";
        String phone1="576875875",phone2="6585745";
        LocalDate date1=LocalDate.of(1999,9,12);
        LocalDate date2=LocalDate.of(1999,7,18);
        Customer customer1=new Customer(1,name1_1,name1_2,phone1,date1);
        Customer customer2=new Customer(2,name2_1,name2_2,phone2,date2);

        //com.nc.unc.model.CustomerRepository list=new com.nc.unc.model.CustomerRepository();
        //list.addCustomer(customer1);
        //list.addCustomer(customer2);
    }
}
