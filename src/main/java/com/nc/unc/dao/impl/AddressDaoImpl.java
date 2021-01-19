package com.nc.unc.dao.impl;

import com.nc.unc.dao.DBConnector;
import com.nc.unc.dao.Dao;
import com.nc.unc.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddressDaoImpl implements Dao<Integer, Address> {

    private final Logger logger = LoggerFactory.getLogger(AddressDaoImpl.class.getSimpleName());

    private static final String INSERT_ADDRESS_TEMPLATE = "insert into store.address(zipcode, address) " +
            "VALUES(?, ?); ";

    private static final String GET_ALL_ADDRESS = "select * from store.address;";

    @Override
    public Map<Integer, Address> getAll() {
        Map<Integer, Address> addresses = new HashMap<>();
        try(Connection connection = DBConnector.connection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(GET_ALL_ADDRESS);
            while (rs.next())
                addresses.put(rs.getInt("id"), addressMapper(rs));
        }catch (SQLException exc){
            logger.error("Get all address into DB exception", exc);
        }
        return addresses;
    }

    @Override
    public Map<Integer, Address>  gelAllWhere(String str) {
        return null;
    }

    public void insert(String address, int zipcode) {
        try (Connection connection = DBConnector.connection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ADDRESS_TEMPLATE)){
            statement.setInt(1, zipcode);
            statement.setString(2, address);
            int rows = statement.executeUpdate();
            logger.info("updated {}", rows);
        }catch (SQLException exc){
            logger.error("Insert {} exception", address, exc);
        }
    }

    private static Address addressMapper(ResultSet rs) throws SQLException {
        return Address.builder()
                .key(rs.getInt("id"))
                .address(rs.getString("address"))
                .zipCode(rs.getInt("zipcode"))
                .build();
    }

}
