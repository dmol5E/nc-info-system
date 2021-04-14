package com.nc.unc;

import com.nc.unc.service.impl.AddressServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StoreApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(StoreApp.class, args);
    }
}
