package com.nc.unc.myService.impl;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.dto.ProductDto;
import com.nc.unc.dto.request.RequestToIncreaseProducts;
import com.nc.unc.model.Product;
import com.nc.unc.model.ProductHistory;
import com.nc.unc.myDao.ProductDao;
import com.nc.unc.myDao.ProductHistoryDao;
import com.nc.unc.myService.IStoreService;
import com.nc.unc.myService.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StoreServiceImpl implements IStoreService {

    private ProductHistoryDao productHistoryDao;
    private ProductDao productDao;
    private ProductMapper modelMapper;

    @Autowired
    public void autowired(ProductDao productDao,
                          ProductHistoryDao productHistoryDao,
                          ProductMapper modelMapper){
        this.modelMapper = modelMapper;
        this.productDao = productDao;
        this.productHistoryDao = productHistoryDao;
    }


    @Override
    public List<ProductDto> getAll() {
        log.debug("StoreServiceImpl.getAll() was invoked");
        return productDao.getAll()
                .stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> findById(int id) {
        log.debug("StoreServiceImpl.findById(int id) was invoked");
        return productDao.find(id)
                .stream()
                .map(modelMapper::toDto)
                .findFirst();
    }

    @Override
    public Optional<ProductDto> increase(RequestToIncreaseProducts requestToIncreaseProducts) {
        log.debug("StoreServiceImpl.increase(RequestToIncreaseProducts requestToIncreaseProducts) was invoked");
        if(requestToIncreaseProducts.getIncreaseCount() < 0)
            throw new IllegalArgumentException();

        Product product = productDao.find(requestToIncreaseProducts.getId()).orElse(null);
        if(product == null)
            throw  new IllegalArgumentException();

        product.setCount(requestToIncreaseProducts.getIncreaseCount());
        productDao.update(product);

        return Optional.ofNullable(modelMapper.toDto(product));
    }

    @Override
    public void put(ProductDto productDto) {
        log.debug("StoreServiceImpl.put(ProductDto productDto) was invoked");
        if(productDto.getCount() < 0 || productDto.getPrice() < 0)
            throw new IllegalArgumentException();
        productHistoryDao.insert(ProductHistory.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build());
        productDao.insert(modelMapper.toEntity(productDto));
    }

    @Override
    public Optional<ProductDto> search(OrderItemDto orderItemDto) {
        return Optional.empty();
    }
}
