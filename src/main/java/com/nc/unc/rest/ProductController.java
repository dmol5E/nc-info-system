package com.nc.unc.rest;


import com.nc.unc.dto.ProductDto;
import com.nc.unc.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private IStoreService storeService;

    @Autowired
    public ProductController(IStoreService storeService){
        this.storeService = storeService;
    }

    @PostMapping
    public void create(@RequestBody ProductDto productDto){
        storeService.put(productDto);
    }

    @GetMapping
    public List<ProductDto> getAll(){
        return storeService.getAll();
    }

    @GetMapping("{id}")
    public ProductDto searchById(@PathVariable("id") int id){
        return storeService.findById(id);
    }

    @GetMapping("/increase")
    public ProductDto increase(@RequestParam("id") int id,
                               @RequestParam("count") int count){
        return storeService.increase(id, count);
    }
}
