package com.nc.unc.dao;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.dto.ProductDto;
import com.nc.unc.model.Product;

import java.util.Optional;

public interface IProductDao extends ICrudDAO<Product> {
    Optional<Product> search(OrderItemDto orderItemDto);
}
