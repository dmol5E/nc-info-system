package com.nc.unc.myService.mapper;

import com.nc.unc.dto.ProductHistoryDto;
import com.nc.unc.model.ProductHistory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductHistoryMapper {

    private final ModelMapper mapper;

    public ProductHistoryMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ProductHistory toEntity(ProductHistoryDto productHistoryDto){
        return Objects.isNull(productHistoryDto) ? null : mapper.map(productHistoryDto, ProductHistory.class);
    }

    public ProductHistoryDto toDto(ProductHistory productHistory){
        return Objects.isNull(productHistory) ? null : mapper.map(productHistory, ProductHistoryDto.class);
    }
}
