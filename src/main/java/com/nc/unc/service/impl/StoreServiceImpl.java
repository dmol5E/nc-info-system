package com.nc.unc.service.impl;

import com.nc.unc.dao.impl.ProductDaoImpl;
import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Product;
import com.nc.unc.service.StoreService;
import java.util.Map;


public class StoreServiceImpl implements StoreService {

    private final ProductDaoImpl productDao;

    public StoreServiceImpl(ProductDaoImpl productDao){
        this.productDao = productDao;
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
        productDao.update(product, product.getKey());
        return product;
    }

    @Override
    public void put(String name, String count, String price) throws BadRequestException {
        if(!name.equals("") && !count.equals("") && !price.equals(""))
            productDao.insert(Product.builder()
                    .name(name)
                    .count(Integer.parseInt(count))
                    .price(Float.parseFloat(price))
                    .build());
        else
            throw new BadRequestException();
    }
}
