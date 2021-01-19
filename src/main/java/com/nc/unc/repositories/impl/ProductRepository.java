package com.nc.unc.repositories.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Product;

import java.beans.ConstructorProperties;
import java.util.Map;

import java.util.stream.Collectors;

public class ProductRepository extends RepositoryEntity<Integer, Product> {
    public ProductRepository() {
        super(ProductRepository.class);
    }

    @JsonCreator
    public ProductRepository(String className){super(ProductRepository.class.getSimpleName());}

    public Product increaseCount(int id, int newCount) {
        Product product;
        if((product = this.entities.get(id)) == null){
            log.warn("Id is wasn't contains {}", id);
            throw new BadRequestException();
        }
        product.setCount(newCount);
        return product;
    }

    public Map<Integer, Product> getProduct(String nameProduct){
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
