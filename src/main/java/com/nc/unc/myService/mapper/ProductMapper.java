package com.nc.unc.myService.mapper;

import com.nc.unc.dto.ProductDto;
import com.nc.unc.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductMapper {

    private final ModelMapper mapper;

    public ProductMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ProductDto toDto(Product product){
        return Objects.isNull(product) ? null : mapper.map(product, ProductDto.class);
    }

    public Product toEntity(ProductDto productDto){
        return Objects.isNull(productDto) ? null : mapper.map(productDto, Product.class);
    }
}
