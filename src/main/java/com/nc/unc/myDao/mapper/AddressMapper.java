package com.nc.unc.myDao.mapper;

import com.nc.unc.model.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet resultSet, int i) throws SQLException {
        return Address.builder()
                .id(resultSet.getInt("id"))
                .zipCode(resultSet.getInt("zipcode"))
                .address(resultSet.getString("address"))
                .build();
    }
}
