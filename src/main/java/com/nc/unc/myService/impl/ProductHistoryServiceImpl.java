package com.nc.unc.myService.impl;

import com.nc.unc.dto.ProductDto;
import com.nc.unc.dto.ProductHistoryDto;
import com.nc.unc.model.ProductHistory;
import com.nc.unc.myDao.ProductHistoryDao;
import com.nc.unc.myService.IProductHistoryService;
import com.nc.unc.myService.mapper.ProductHistoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductHistoryServiceImpl implements IProductHistoryService {

    private ProductHistoryMapper productHistoryMapper;
    private ProductHistoryDao productDao;

    @Autowired
    public void autowired(ProductHistoryMapper productHistoryMapper,
                          ProductHistoryDao productDao){
        this.productDao = productDao;
        this.productHistoryMapper = productHistoryMapper;
    }

    @Override
    public void put(ProductDto product) {
        log.debug("ProductHistoryImpl.put(ProductDto product) was invoked");
        ProductHistory productHistory =
                ProductHistory.builder()
                        .price(product.getPrice())
                        .name(product.getName())
                        .build();
        productDao.insert(productHistory);
    }

    @Override
    public Optional<ProductHistoryDto> searchById(int id) {
        log.debug("ProductHistoryImpl.searchById(int id) was invoked");
        return productDao.find(id)
                .stream()
                .map(productHistoryMapper::toDto)
                .findFirst();
    }

    @Override
    public Optional<ProductHistoryDto> searchByOrderItem(String name, float price) {
        log.debug("ProductHistoryImpl.searchByOrderItem(String name, float price) was invoked");
        return productDao.searchOrderItem(name, price)
                .stream()
                .map(productHistoryMapper::toDto)
                .findFirst();
    }

    @Override
    public Optional<ProductHistoryDto> searchByProduct(String name, float price) {
        log.debug("ProductHistoryImpl.searchByProduct(String name, float price) was invoked");
        return productDao.searchOrderItem(name, price)
                .stream()
                .map(productHistoryMapper::toDto)
                .findFirst();
    }

    @Override
    public List<ProductHistoryDto> getAll() {
        log.debug("ProductHistoryImpl.getAll() was invoked");
        return productDao.getAll()
                .stream()
                .map(productHistoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
