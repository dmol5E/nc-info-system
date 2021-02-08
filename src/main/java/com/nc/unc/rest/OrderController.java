package com.nc.unc.rest;

import com.nc.unc.dto.OrderDto;
import com.nc.unc.myService.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Scope("session")
public class OrderController {
    private IOrderService orderService;

    @Autowired
    private OrderController(IOrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDto> orderDto(){
        return orderService.getAll();
    }

}
