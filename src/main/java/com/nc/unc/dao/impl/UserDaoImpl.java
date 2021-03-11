package com.nc.unc.dao.impl;

import com.nc.unc.dao.IUserDao;
import com.nc.unc.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends CrudDaoImpl<User> implements IUserDao {
}
