package com.nc.unc.dao.mapper;

import com.nc.unc.model.ProductHistory;
import com.nc.unc.dao.IOrderItemDao;
import com.nc.unc.dao.template.EntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductHistoryDaoMapper extends AbstractMapper<ProductHistory>{

    private IOrderItemDao orderItemDao;

    @Autowired
    public void set(IOrderItemDao orderItemDao){
        this.orderItemDao = orderItemDao;
    }

    public ProductHistoryDaoMapper() {
        super(ProductHistory.class, new EntityImpl<>(ProductHistory.class));
    }

    @Override
    public ProductHistory mapRow(ResultSet rs, int i) throws SQLException {
        ProductHistory productHistory = super.mapRow(rs, i);
        if(productHistory != null)
            productHistory.setOrderItems(orderItemDao.getByProductHistory(productHistory.getId()));
        return productHistory;
    }
}
