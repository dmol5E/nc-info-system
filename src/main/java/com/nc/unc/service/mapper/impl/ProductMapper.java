package com.nc.unc.service.mapper.impl;

import com.nc.unc.dto.ProductDto;
import com.nc.unc.model.Product;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product){
        return Objects.isNull(product) ? null :
                ProductDto.builder()
                        .id(product.getId())
                        .count(product.getCount())
                        .name(product.getName())
                        .price(product.getPrice())
                        .build();
    }

    public Product toEntity(ProductDto productDto){
        return Objects.isNull(productDto) ? null :
                Product.builder()
                        .id(productDto.getId())
                        .name(productDto.getName())
                        .price(productDto.getPrice())
                        .count(productDto.getCount())
                        .build();
    }
}
