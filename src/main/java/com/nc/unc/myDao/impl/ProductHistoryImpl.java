package com.nc.unc.myDao.impl;



import com.nc.unc.model.ProductHistory;
import com.nc.unc.myDao.ProductHistoryDao;
import com.nc.unc.myDao.mapper.ProductHistoryDaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@PropertySource("classpath:db/sql/ProductHistorySQL.properties")
public class ProductHistoryImpl extends CrudDaoImpl<ProductHistory> implements ProductHistoryDao {

    @Value("${SELECT_BY_PRODUCT}")
    private String SELECT_BY_PRODUCT;


    @Autowired
    public void setAbstractMapper(ProductHistoryDaoMapper productHistoryDaoMapper) {
        super.setAbstractMapper(productHistoryDaoMapper);
    }

    @Override
    public Optional<ProductHistory> searchOrderItem(String name, float price) {
        return Optional.ofNullable(getJdbcTemplate().queryForObject(SELECT_BY_PRODUCT,
                new Object[]{name, price},
                getAbstractMapper()));
    }

    @Override
    public Optional<ProductHistory> searchProduct(String name, float price) {
        return Optional.ofNullable(getJdbcTemplate().queryForObject(SELECT_BY_PRODUCT,
                new Object[]{name, price},
                getAbstractMapper()));
    }
}
