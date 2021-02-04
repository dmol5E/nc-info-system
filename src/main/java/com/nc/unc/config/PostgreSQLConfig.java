package com.nc.unc.config;

import lombok.AllArgsConstructor;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

@AllArgsConstructor
public class PostgreSQLConfig {


    private String servername;
    private Integer port;
    private String database;
    private String username;
    private String password;

    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(servername);
        dataSource.setUser(username);
        dataSource.setDatabaseName(database);
        dataSource.setPassword(password);
        dataSource.setPortNumber(port);
        return dataSource;
    }
}
