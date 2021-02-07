package com.nc.unc.myService;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.dto.ProductDto;
import com.nc.unc.dto.request.RequestToIncreaseProducts;

import java.util.List;
import java.util.Optional;

public interface IStoreService {
    List<ProductDto> getAll();
    Optional<ProductDto> findById(int id);
    Optional<ProductDto> increase(RequestToIncreaseProducts productDto);
    void put(ProductDto productDto);
    Optional<ProductDto> search(OrderItemDto orderItemDto);
}
