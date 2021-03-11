package com.nc.unc.dao.mapper;

import com.nc.unc.dao.template.Entity;
import com.nc.unc.dao.template.EntityImpl;
import com.nc.unc.model.ProductHistory;
import org.springframework.stereotype.Component;
import com.nc.unc.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper extends AbstractMapper<User>{

    public UserMapper() {
        super(User.class, new EntityImpl<>(User.class));
    }
    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        return super.mapRow(rs, i);
    }
}
