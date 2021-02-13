package com.nc.unc.myDao.impl;

import com.nc.unc.model.Product;
import com.nc.unc.myDao.IProductDao;
import org.springframework.stereotype.Repository;

@Repository
public class IProductDaoImpl extends CrudDaoImpl<Product> implements IProductDao {

}
