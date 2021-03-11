package com.nc.unc.model;

import com.nc.unc.dao.annotation.Attribute;
import com.nc.unc.dao.annotation.Table;
import lombok.Data;

@Table(value = "user_role", schema = "store")
@Data
public class UserRole extends Role {

    @Attribute("fk_role")
    private int fk_role;

    @Attribute("fk_user")
    private int fk_user;

    private Role role;
    private User user;

}
