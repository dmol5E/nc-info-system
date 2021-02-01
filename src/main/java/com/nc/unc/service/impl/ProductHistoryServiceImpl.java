package com.nc.unc.service.impl;

import com.nc.unc.dao.ProductHistoryDao;
import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;
import com.nc.unc.model.ProductHistory;
import com.nc.unc.service.ProductHistoryService;

import java.util.Map;
import java.util.Optional;

public class ProductHistoryServiceImpl implements ProductHistoryService {

    private ProductHistoryDao productHistoryDao;

    public ProductHistoryServiceImpl(ProductHistoryDao productHistoryDao){
        this.productHistoryDao = productHistoryDao;
    }


    @Override
    public void put(Product product) {
        productHistoryDao.insert(ProductHistory.builder()
                .name(product.getName())
                .price(product.getPrice())
                .build());
    }

    @Override
    public Optional<ProductHistory> searchById(int id) {
        return productHistoryDao.getByKey(id);
    }

    @Override
    public Optional<ProductHistory> searchProductHistory(Product product) {
        return productHistoryDao.search(product);
    }

    @Override
    public Optional<ProductHistory> searchProductHistory(OrderItem orderItem) {
        return productHistoryDao.search(orderItem);
    }

    @Override
    public Map<Integer, ProductHistory> getAll() {
        return productHistoryDao.getAll();
    }
}
