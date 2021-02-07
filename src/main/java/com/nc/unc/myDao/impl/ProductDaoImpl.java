package com.nc.unc.myDao.impl;

import com.nc.unc.model.Product;
import com.nc.unc.myDao.ProductDao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ProductDaoImpl extends CrudDaoImpl<Product> implements ProductDao {

}
