package com.nc.unc.myDao.mapper;

import com.nc.unc.model.Product;
import com.nc.unc.myDao.template.EntityImpl;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductDaoMapper extends AbstractMapper<Product>{

    public ProductDaoMapper() {
        super(Product.class, new EntityImpl<>(Product.class));
    }

    @Override
    public Product mapRow(ResultSet rs, int i) throws SQLException {
        return super.mapRow(rs, i);
    }


}
