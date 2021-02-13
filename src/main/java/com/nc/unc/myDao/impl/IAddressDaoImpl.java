package com.nc.unc.myDao.impl;

import com.nc.unc.model.Address;
import com.nc.unc.myDao.IAddressDao;
import com.nc.unc.myDao.mapper.AddressDaoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@PropertySource("classpath:db/sql/AddressSQL.properties")
public class IAddressDaoImpl extends CrudDaoImpl<Address> implements IAddressDao {

    @Value("${SELECT_BY_ADDRESS_ZIPCODE}")
    private String SELECT_BY_ADDRESS_ZIPCODE;

    @Value("${SELECT_BY_ADDRESS}")
    private String SELECT_BY_ADDRESS;

    @Value("${SELECT_BY_ZIPCODE}")
    private String SELECT_BY_ZIPCODE;


    @Override
    public  Optional<Address> searchByAddressZipcode(String address, int zipcode) {
        log.debug("SQL {} Search by address {} zipcode {}",
                SELECT_BY_ADDRESS_ZIPCODE, address, zipcode);
        return Optional.ofNullable(getJdbcTemplate().queryForObject(SELECT_BY_ADDRESS_ZIPCODE,
                new Object[]{address, zipcode},
                getAbstractMapper()));
    }

    @Override
    public Optional<Address> searchByAddress(String address) {
        log.debug("SQL {} Search by address {}",
                SELECT_BY_ADDRESS, address);
        return Optional.ofNullable(getJdbcTemplate().queryForObject(SELECT_BY_ADDRESS,
                new Object[]{address},
                getAbstractMapper()));
    }

    @Override
    public Optional<Address> searchByZipcode(int zipcode) {
        log.debug("SQL {} Search by zipcode {}",
                SELECT_BY_ZIPCODE, zipcode);
        return Optional.ofNullable(getJdbcTemplate().queryForObject(SELECT_BY_ZIPCODE,
                new Object[]{zipcode},
                getAbstractMapper()));
    }

    @Autowired
    public void setAbstractMapper(AddressDaoMapper abstractMapper) {
        super.setAbstractMapper(abstractMapper);
    }

}
