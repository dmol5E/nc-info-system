package com.nc.unc.rest;

import com.nc.unc.model.Order;
import com.nc.unc.myDao.*;
import com.nc.unc.util.json.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api")
public class Test {
    private AddressDao addressDao;
    private CustomerDao customerDao;
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;
    private ProductDao productDao;
    private ProductHistoryDao productHistoryDao;

    @Autowired
    private void set(OrderDao orderDao){
        this.orderDao = orderDao;
    }

    @RequestMapping(
            path = "/connect",
            produces = MediaType.TEXT_PLAIN_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<String> get(){
        List<Order> list = orderDao.getAll();
        return ResponseEntity.ok().body(JsonHelper.toJson(list));
    }

}
