package com.nc.unc.myDao.impl;


import com.nc.unc.model.Product;
import com.nc.unc.myDao.ProductHistoryDao;

import javax.sql.DataSource;

public class ProductHistoryImpl extends CrudDaoImpl<Product> implements ProductHistoryDao {
    public ProductHistoryImpl(DataSource dataSource) {
        super(dataSource);
    }
}
