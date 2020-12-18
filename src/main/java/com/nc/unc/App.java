package com.nc.unc;

import com.nc.unc.model.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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

    public static void main( String[] args ) {
        System.out.println(getCustomer("one").toString());
        String a = "dsafF D S";
        System.out.println(a.toLowerCase().contains("aff"));

        //List<Customer> list = Stream.of(new Customer(1, "1","23",new Date(321312)))
        //        .collect(Collectors.toList());
    }
}
