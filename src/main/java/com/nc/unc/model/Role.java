package com.nc.unc.model;

import com.nc.unc.dao.annotation.Attribute;
import com.nc.unc.dao.annotation.Table;
import lombok.Data;

@Table(value = "role",  schema = "store")
@Data
public class Role extends BaseEntity {

    @Attribute("name")
    private String name;
}
