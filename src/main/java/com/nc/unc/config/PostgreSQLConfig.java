package com.nc.unc.config;


import lombok.NoArgsConstructor;
import lombok.Setter;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;

@NoArgsConstructor
@Configuration
@ConfigurationProperties("postgres")
@EnableConfigurationProperties(PostgreSQLConfig.class)
@Setter
public class PostgreSQLConfig {


    private String servername;
    private Integer port;
    private String database;
    private String username;
    private String password;

    @Bean
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
