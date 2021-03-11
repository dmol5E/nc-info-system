package com.nc.unc.dao.mapper;

import com.nc.unc.model.Customer;
import com.nc.unc.dao.template.EntityImpl;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerDaoMapper extends AbstractMapper<Customer>{
    public CustomerDaoMapper() {
        super(Customer.class, new EntityImpl<>(Customer.class));
    }

    @Override
    public Customer mapRow(ResultSet rs, int i) throws SQLException {
        return super.mapRow(rs, i);
    }
}
