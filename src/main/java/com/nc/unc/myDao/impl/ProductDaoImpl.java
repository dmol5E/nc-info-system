package com.nc.unc.myDao.impl;

import com.nc.unc.model.Product;
import com.nc.unc.myDao.ProductDao;

import javax.sql.DataSource;

public class ProductDaoImpl extends CrudDaoImpl<Product> implements ProductDao {
    public ProductDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
