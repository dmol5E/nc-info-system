package com.nc.unc.myService;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.dto.ProductDto;

import java.util.List;

public interface IStorageService {
    void formAnOrder(int id);
    void putOrderItem(ProductDto product, int increase);
    int size();
    float getPrice();
    void removeOrderItem(int id);
    List<OrderItemDto> get();
}
