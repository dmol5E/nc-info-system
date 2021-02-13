package com.nc.unc.rest;

import com.nc.unc.dto.AddressDto;
import com.nc.unc.dto.CustomerDto;
import com.nc.unc.dto.OrderDto;
import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.myService.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("storage/put")
    public void putOrderItem(@RequestParam("id") int id, @RequestParam("increase") int increase){
        orderService.putOrderItem(id, increase);
    }

    @GetMapping("storage/rem")
    public void remove(@RequestParam("id") int id){
        orderService.removeOrderItem(id);
    }

    @GetMapping("storage")
    public List<OrderItemDto> getStorage(){
        return orderService.getStorage();
    }

    @PostMapping("customer")
    public void putCustomer(@RequestBody CustomerDto customerDto){
        orderService.putCustomer(customerDto);
    }

    @PostMapping("create")
    public void createNewOrder(AddressDto address){
        orderService.createNewOrder(address);
    }

    @GetMapping("find")
    public OrderDto findOrderById(@RequestParam("id") int id){
        return orderService.findOrderById(id);
    }

    @GetMapping("storage/sum")
    public double getSum(){
        return orderService.getSum();
    }

    @GetMapping("customer")
    public CustomerDto getCustomer(){
        return orderService.getOrderCustomer();
    }
}
