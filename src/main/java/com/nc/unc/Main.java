package com.nc.unc;


import com.nc.unc.config.PostgreSQLConfig;
import com.nc.unc.model.Address;
import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.myDao.AddressDao;
import com.nc.unc.myDao.CustomerDao;
import com.nc.unc.myDao.OrderDao;
import com.nc.unc.myDao.OrderItemDao;
import com.nc.unc.myDao.annotation.Enumerated;
import com.nc.unc.myDao.impl.AddressDaoImpl;
import com.nc.unc.myDao.impl.CustomerDaoImpl;
import com.nc.unc.myDao.impl.OrderDaoImpl;
import com.nc.unc.myDao.template.EntityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class Main  {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class.getSimpleName());
        PostgreSQLConfig postgreSQLTemplate =
                new PostgreSQLConfig("localhost", 5432, "netcracker", "postgres", "123");
        AddressDao addressDao = new AddressDaoImpl(postgreSQLTemplate.dataSource());

        Address customer = addressDao.find(1).orElse(null);
        System.out.println(customer);
    }
}
