package com.nc.unc;

import com.nc.unc.dao.template.Entity;
import com.nc.unc.dao.template.EntityImpl;
import com.nc.unc.model.Role;

public class Main {
    public static void main(String[] args) {
        Entity<Role> roleEntity = new EntityImpl<>(Role.class);

    }
}
