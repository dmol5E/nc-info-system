package com.nc.unc.service;


import com.nc.unc.dto.ProductDto;
import com.nc.unc.dto.ProductHistoryDto;


import java.util.List;

public interface IProductHistoryService {
    void put(ProductDto product);
    ProductHistoryDto searchById(int id);
    ProductHistoryDto searchByOrderItem(String name, float price);
    ProductHistoryDto searchByProduct(String name, float price);
    List<ProductHistoryDto> getAll();
}
