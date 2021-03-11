package com.nc.unc.dao.impl;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.model.Address;
import com.nc.unc.model.Product;
import com.nc.unc.dao.IProductDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class IProductDaoImpl extends CrudDaoImpl<Product> implements IProductDao {

    private final String SEARCH_BY_ORDER_ITEM = "select * from store.product where name = ? and price = ?";

    @Override
    public Optional<Product> search(OrderItemDto orderItemDto) {
        try {
            Product productDB = getJdbcTemplate().queryForObject(SEARCH_BY_ORDER_ITEM,
                    new Object[]{orderItemDto.getName(), orderItemDto.getPrice()},
                    getAbstractMapper());

            return Optional.ofNullable(productDB);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
}
