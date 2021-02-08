package com.nc.unc.dao.impl;

import com.nc.unc.dao.AddressDao;
import client.util.jdbc.DBConnector;
import com.nc.unc.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AddressDaoImpl implements AddressDao {

    private final Logger logger = LoggerFactory.getLogger(AddressDaoImpl.class.getSimpleName());


    private static final String GET_BY_ID =
            "select * from store.address where id = ?;";

    private static final String UPDATE_ADDRESS_TEMPLATE =
            "update store.address " +
                    "set zipcode = ?, address = ? " +
                    "where id = ?";

    private static final String SEARCH_ADDRESS =
            "";

    private static final String INSERT_ADDRESS_TEMPLATE =
            "insert into store.address(zipcode, address) " +
                    "VALUES(?, ?); ";

    private static final String GET_ALL_ADDRESS =
            "select * from store.address;";




    public void update(Address address, Integer id){
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS_TEMPLATE)) {
            statement.setInt(1, address.getZipCode());
            statement.setString(2, address.getAddress());
            statement.setInt(3, id);

            statement.executeUpdate();
        }catch (SQLException exc){
            logger.info("Exception update address {} ", address, exc);
        }
    }

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

    public Address insertRecovery(Address address) {
        Address oAddress = null;
        try (Connection connection = DBConnector.connection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_ADDRESS_TEMPLATE, new String[] { "id", "address", "zipcode" })){
            statement.setInt(1, address.getZipCode());
            statement.setString(2, address.getAddress());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) oAddress = addressMapper(rs);
        }catch (SQLException exc){
            logger.error("Insert {} exception", address, exc);
        }
        return oAddress;
    }

    public void insert(Address address) {
        try (Connection connection = DBConnector.connection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ADDRESS_TEMPLATE)){
            statement.setInt(1, address.getZipCode());
            statement.setString(2, address.getAddress());
            statement.execute();
        }catch (SQLException exc){
            logger.error("Insert {} exception", address, exc);
        }
    }

    public Optional<Address> search(String address, int zipcode){
        Address oAddress = null;
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(SEARCH_ADDRESS)){
            statement.setString(1, address);
            statement.setInt(2, zipcode);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) oAddress = addressMapper(rs);
        }catch (SQLException exc){
            logger.info("Search address exception");
        }
        return Optional.ofNullable(
                oAddress
        );
    }


    @Override
    public Optional<Address> getByKey(Integer id) {
        Address address = null;
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
                address = addressMapper(rs);
        }catch (SQLException exc){
            logger.info("Get all address by id {} exception", id, exc);
        }
        return Optional.ofNullable(
                address
        );
    }



    @Override
    public Optional<Address> searchByAddress(String address) {
        String searchByAddressSQL = "select * from store.address where address.address = ?";
        Address addressFromBD = null;

        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(searchByAddressSQL)) {
            statement.setString(1, address);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) addressFromBD = addressMapper(rs);
        }catch (SQLException exc){
            logger.info("search By Address {} Exception ", address, exc);
        }
        return Optional.ofNullable(addressFromBD);
    }

    @Override
    public Optional<Address> searchByZipCode(int zipcode) {
        String searchByZipCodeSQL = "select * from store.address where zipcode = ?";
        Address addressFromBD = null;

        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(searchByZipCodeSQL)) {
            statement.setInt(1, zipcode);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) addressFromBD = addressMapper(rs);
        }catch (SQLException exc){
            logger.info("search By ZipCode {} Exception ", zipcode, exc);
        }
        return Optional.ofNullable(addressFromBD);
    }

    private static Address addressMapper(ResultSet rs) throws SQLException {
        return Address.builder()
                .id(rs.getInt("id"))
                .address(rs.getString("address"))
                .zipCode(rs.getInt("zipcode"))
                .build();
    }


}
