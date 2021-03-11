package com.nc.unc.service;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.dto.ProductDto;

import java.util.List;

public interface IStoreService {
    List<ProductDto> getAll();
    ProductDto findById(int id);
    ProductDto increase(int id, int count);
    ProductDto decrease(int id, int count);
    void put(ProductDto productDto);
    ProductDto search(OrderItemDto orderItemDto);
}
