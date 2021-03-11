package com.nc.unc.dao;

import com.nc.unc.model.Role;
import com.nc.unc.model.UserRole;

public interface IRoleUserDao extends ICrudDAO<Role> {
    UserRole findByUserId();
    UserRole findByRoleId();
}
