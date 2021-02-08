package com.nc.unc.myService;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.dto.ProductDto;
import com.nc.unc.dto.request.RequestToIncreaseProducts;

import java.util.List;
import java.util.Optional;

public interface IStoreService {
    List<ProductDto> getAll();
    ProductDto findById(int id);
    ProductDto increase(int id, int count);
    ProductDto decrease(int id, int count);
    void put(ProductDto productDto);
    ProductDto search(OrderItemDto orderItemDto);
}
