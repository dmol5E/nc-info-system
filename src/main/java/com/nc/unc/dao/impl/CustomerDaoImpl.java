package com.nc.unc.dao.impl;

import com.nc.unc.dao.DBConnector;
import com.nc.unc.dao.Dao;
import com.nc.unc.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CustomerDaoImpl implements Dao<Integer, Customer> {

    private static Logger log = LoggerFactory.getLogger(CustomerDaoImpl.class.getSimpleName());

    private final static String GET_ALL_CUSTOMER = "select * from store.customer";

    private final static String INSERT_CUSTOMER_TEMPLATE = "insert into "
            + "store.customer(first_name, last_name, phone_number, date) " +
            "values (?,?,?,?);";

    @Override
    public Map<Integer, Customer> getAll() {
        Map<Integer, Customer> res = new HashMap<>();
        try(Connection connection = DBConnector.connection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(GET_ALL_CUSTOMER);
            while (rs.next())
                res.put(rs.getInt("id"), customerMapper(rs));
        }catch (SQLException exc){
            log.info("Insert into customer exception", exc);
        }
        return res;
    }

    @Override
    public Map<Integer, Customer> gelAllWhere(String str) {
        return null;
    }

    public void insert(String firstName, String lastName, String phoneNumber, LocalDate date){
        try(Connection connection = DBConnector.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_TEMPLATE)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setDate(4, Date.valueOf(date));

            preparedStatement.executeUpdate();
        }catch (SQLException exc){
            log.info("Insert into customer exception", exc);
        }
    }

    private static Customer customerMapper(ResultSet rs) throws SQLException{
        return Customer.builder()
                .key(rs.getInt("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .phoneNumber(rs.getString("phone_number"))
                .data(rs.getDate("date").toLocalDate())
                .build();
    }

}
