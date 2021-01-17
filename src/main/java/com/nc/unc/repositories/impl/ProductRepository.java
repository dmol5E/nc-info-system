package com.nc.unc.repositories.impl;

import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Product;

import java.beans.ConstructorProperties;
import java.util.Map;

import java.util.stream.Collectors;

public class ProductRepository extends RepositoryEntity<Long, Product> {
    public ProductRepository() {
        super(ProductRepository.class);
    }

    @ConstructorProperties({"type"})
    public ProductRepository(String className){super(className);}

    public Product increaseCount(long id, int newCount) {
        Product product;
        if((product = this.entities.get(id)) == null){
            log.warn("Id is wasn't contains {}", id);
            throw new BadRequestException();
        }
        return product.setCount(newCount);
    }

    public Map<Long, Product> getProduct(String nameProduct){
        String product = nameProduct.toLowerCase();
        return this.entities.entrySet().stream()
                .filter(entry -> entry.getValue().getName().toLowerCase().contains(product))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public String toString() {
        return "ProductRepository{ " +
                super.toString() + " \n}";
    }
}
