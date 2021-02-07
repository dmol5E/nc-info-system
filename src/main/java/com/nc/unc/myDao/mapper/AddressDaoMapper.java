package com.nc.unc.myDao.mapper;

import com.nc.unc.model.Address;

import com.nc.unc.myDao.template.EntityImpl;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AddressDaoMapper extends AbstractMapper<Address> {

    public AddressDaoMapper() {
        super(Address.class, new EntityImpl<>(Address.class));
    }

    @Override
    public Address mapRow(ResultSet rs, int i) throws SQLException {
        return super.mapRow(rs, i);
    }
}
