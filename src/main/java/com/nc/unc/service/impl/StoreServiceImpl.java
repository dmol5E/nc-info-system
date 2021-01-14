package com.nc.unc.service.impl;

import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Product;
import com.nc.unc.repositories.impl.ProductRepository;
import com.nc.unc.service.StoreService;

import java.util.Map;

public class StoreServiceImpl implements StoreService {

    private final ProductRepository repository = new ProductRepository();

    @Override
    public Map<Long, Product> getAll() {
        return repository.getEntities();
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public Product update(Long id, int count) throws BadRequestException {
        if(id < 0 || count < 0)
            throw new BadRequestException();
        return repository.increaseCount(id, count);
    }

    @Override
    public int size() {
        return repository.size();
    }

    @Override
    public void put(String name, String count, String price) throws BadRequestException {
        if(!name.equals("") && !count.equals("") && !price.equals(""))
            repository.put(new Product(repository.size(),
                    Integer.parseInt(count), name,
                    Float.parseFloat(price)));
        else
            throw new BadRequestException();
    }
}
