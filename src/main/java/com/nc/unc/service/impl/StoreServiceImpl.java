package com.nc.unc.service.impl;

import com.nc.unc.dao.ProductDao;
import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;
import com.nc.unc.model.ProductHistory;
import com.nc.unc.service.ProductHistoryService;
import com.nc.unc.service.StoreService;
import java.util.Map;
import java.util.Optional;


public class StoreServiceImpl implements StoreService {
    private final ProductHistoryService productHistoryService;
    private final ProductDao productDao;

    public StoreServiceImpl(ProductDao productDao,
                            ProductHistoryService productHistoryService){
        this.productHistoryService = productHistoryService;
        this.productDao = productDao;
    }


    @Override
    public Optional<ProductHistory> searchProductHistory(OrderItem orderItem) {
        return productHistoryService.searchProductHistory(orderItem);
    }

    @Override
    public Map<Integer, Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public Product findById(int id) {
        return productDao.getByKey(id).orElse(null);
    }

    @Override
    public Product update(int id, int count) throws BadRequestException {
        Product product = productDao.getByKey(id).get(); int newCount;
        if(id < 0 || (newCount = (product.getCount() + count)) < 0)
            throw new BadRequestException();
        product.setCount(newCount);
        productDao.update(product, product.getId());
        return product;
    }

    @Override
    public void put(String name, String count, String price) throws BadRequestException {
        if(!name.equals("") || !count.equals("") || !price.equals("")) {
            Product product = Product.builder()
                    .name(name)
                    .count(Integer.parseInt(count))
                    .price(Float.parseFloat(price))
                    .build();

            productDao.insert(product);
            productHistoryService.put(product);
        }
        else
            throw new BadRequestException();
    }

    @Override
    public Optional<Product> search(OrderItem orderItem) {
        return productDao.search(orderItem);
    }
}
