package com.nc.unc.service.impl;

import com.nc.unc.dto.ProductDto;
import com.nc.unc.dto.ProductHistoryDto;
import com.nc.unc.exception.RequestException;
import com.nc.unc.model.ProductHistory;
import com.nc.unc.dao.IProductHistoryDao;
import com.nc.unc.service.IProductHistoryService;
import com.nc.unc.service.mapper.impl.ProductHistoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductHistoryServiceImpl implements IProductHistoryService {

    private ProductHistoryMapper productHistoryMapper;
    private IProductHistoryDao productDao;

    @Autowired
    public ProductHistoryServiceImpl(ProductHistoryMapper productHistoryMapper,
                                     IProductHistoryDao productDao){
        this.productDao = productDao;
        this.productHistoryMapper = productHistoryMapper;
    }

    @Override
    public void put(ProductDto product) {
        log.debug("ProductHistoryImpl.put(ProductDto product) was invoked");

        ProductHistory productHistory = ProductHistory.builder()
                .price(product.getPrice())
                .name(product.getName())
                .build();
        productDao.insert(productHistory);
    }

    @Override
    public ProductHistoryDto searchById(int id) {
        log.debug("ProductHistoryImpl.searchById(int id) was invoked");
        Optional<ProductHistory> optionalProductHistory = productDao.find(id);

        if(optionalProductHistory.isEmpty()){
            log.error("No such ProductHistory by id {}", id);
            throw new RequestException("No such ProductHistory", HttpStatus.BAD_REQUEST);
        }

        return optionalProductHistory.stream()
                .map(productHistoryMapper::toDto)
                .findFirst().get();
    }

    @Override
    public ProductHistoryDto searchByOrderItem(String name, float price) {
        log.debug("ProductHistoryImpl.searchByOrderItem(String name, float price) was invoked");
        Optional<ProductHistory> optionalProductHistory = productDao.searchOrderItem(name, price);

        if(optionalProductHistory.isEmpty()){
            log.error("No such ProductHistory by name {} price {}", name, price);
            throw new RequestException("No such ProductHistory", HttpStatus.BAD_REQUEST);
        }

        return optionalProductHistory.stream()
                .map(productHistoryMapper::toDto)
                .findFirst().get();
    }

    @Override
    public ProductHistoryDto searchByProduct(String name, float price) {
        log.debug("ProductHistoryImpl.searchByProduct(String name, float price) was invoked");
        Optional<ProductHistory> optionalProductHistory = productDao.searchProduct(name, price);
        if(optionalProductHistory.isEmpty()){
            log.error("No such ProductHistory by name {} price {}", name, price);
            throw new RequestException("No such ProductHistory", HttpStatus.BAD_REQUEST);
        }

        return optionalProductHistory.stream()
                .map(productHistoryMapper::toDto)
                .findFirst().get();
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
