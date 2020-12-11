package com.nc.unc.repositories;

import com.nc.unc.model.Product;

import java.util.Map;

import org.apache.logging.log4j.Level;
import java.util.stream.Collectors;

public class ProductRepository extends RepositoryEntity<Long, Product> {
    public ProductRepository() {
        super(ProductRepository.class);
    }

    public void increaseCount(long id, int newCount) {
        Product product;
        if((product = this.entities.get(id)) == null){
            logger.log(Level.WARN, " Id is wasn't contains " + id);
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
