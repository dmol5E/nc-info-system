package com.nc.unc.myDao.mapper;

import com.nc.unc.model.ProductHistory;
import com.nc.unc.myDao.OrderItemDao;
import com.nc.unc.myDao.template.Entity;
import com.nc.unc.myDao.template.EntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductHistoryDaoMapper extends AbstractMapper<ProductHistory>{

    private OrderItemDao orderItemDao;

    @Autowired
    public void set(OrderItemDao orderItemDao){
        this.orderItemDao = orderItemDao;
    }

    public ProductHistoryDaoMapper() {
        super(ProductHistory.class, new EntityImpl<>(ProductHistory.class));
    }

    @Override
    public ProductHistory mapRow(ResultSet rs, int i) throws SQLException {
        ProductHistory productHistory = super.mapRow(rs, i);
        if(productHistory != null)
            productHistory.setOrderItem(orderItemDao.getByProductHistory(productHistory.getId()));
        return productHistory;
    }
}
