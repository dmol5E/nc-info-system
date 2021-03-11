package com.nc.unc.service.impl;

import com.nc.unc.dao.IRoleDao;
import com.nc.unc.dao.IRoleUserDao;
import com.nc.unc.dao.IUserDao;
import com.nc.unc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserDao userDao;

    private final IRoleDao roleDao;

    private final IRoleUserDao roleUserDao;


    @Autowired
    public UserServiceImpl(IUserDao userDao,
                           IRoleDao roleDao,
                           IRoleUserDao roleUserDao){
        this.roleDao = roleDao;
        this.roleUserDao = roleUserDao;
        this.userDao = userDao;
    }


}
