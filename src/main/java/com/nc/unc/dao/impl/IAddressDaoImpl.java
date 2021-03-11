package com.nc.unc.dao.impl;

import com.nc.unc.model.Address;
import com.nc.unc.dao.IAddressDao;
import com.nc.unc.dao.mapper.AddressDaoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

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
    public  Optional<Address> searchByAddressZipcode(String address, String zipcode) {
        log.debug("SQL {} Search by address {} zipcode {}",
                SELECT_BY_ADDRESS_ZIPCODE, address, zipcode);
        try {
            Address addressDB = getJdbcTemplate().queryForObject(SELECT_BY_ADDRESS_ZIPCODE,
                    new Object[]{address, zipcode},
                    getAbstractMapper());
            return Optional.ofNullable(addressDB);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Address> searchByAddress(String address) {
        log.debug("SQL {} Search by address {}",
                SELECT_BY_ADDRESS, address);
        try {
            Address addressDB = getJdbcTemplate().queryForObject(SELECT_BY_ADDRESS,
                    new Object[]{address},
                    getAbstractMapper());

            return Optional.ofNullable(addressDB);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Address> searchByZipcode(String zipcode) {
        log.debug("SQL {} Search by zipcode {}",
                SELECT_BY_ZIPCODE, zipcode);

        try {
            Address addressDB = getJdbcTemplate().queryForObject(SELECT_BY_ZIPCODE,
                    new Object[]{zipcode},
                    getAbstractMapper());

            return Optional.ofNullable(addressDB);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Autowired
    public void setAbstractMapper(AddressDaoMapper abstractMapper) {
        super.setAbstractMapper(abstractMapper);
    }

}
