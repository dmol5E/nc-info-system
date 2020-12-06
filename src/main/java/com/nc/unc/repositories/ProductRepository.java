package com.nc.unc.repositories;

import com.nc.unc.model.Product;

import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ProductRepository extends Repository<Long, Product> {
    public ProductRepository(String fileName) {
        super(fileName, ProductRepository.class);
    }

    public void increaseCount(long id, int newCount) {
        Product product;
        if((product = this.entities.get(id)) == null){
            logger.log(Level.WARNING, " Id is wasn't contains " + id);
            throw new IllegalArgumentException();
        }
        product.setCount(newCount);
    }

    public Map<Long, Product> getProduct(String nameProduct){
        String product = nameProduct.toLowerCase();
        return this.entities.entrySet().stream()
                .filter(entry -> entry.getValue().getName().toLowerCase().contains(product))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
