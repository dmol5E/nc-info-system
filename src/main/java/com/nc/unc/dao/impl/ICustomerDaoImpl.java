package com.nc.unc.dao.impl;


import com.nc.unc.model.Customer;
import com.nc.unc.dao.ICustomerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@PropertySource("classpath:db/sql/clients.properties")
public class ICustomerDaoImpl extends CrudDaoImpl<Customer> implements ICustomerDao {

    @Value("${SELECT_BY_NAME}")
    private String FIND_BY_NAME;

    @Override
    public List<Customer> findByName(String name) {
        log.debug("SQL {} findByName by name {}",
                FIND_BY_NAME, name);

        return getJdbcTemplate().query(FIND_BY_NAME,
                new Object[]{name},
                getAbstractMapper());
    }
}
