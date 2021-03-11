package com.nc.unc.model;

import com.nc.unc.dao.annotation.Attribute;
import com.nc.unc.dao.annotation.PrimaryKey;
import com.nc.unc.dao.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user", schema = "store")
public class User extends BaseEntity {

    @Attribute("login")
    private String login;

    @Attribute("firstName")
    private String firstName;

    @Attribute("lastName")
    private String lastName;

    @Attribute("password")
    private String password;

    private List<Role> roles;
}
