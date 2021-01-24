package com.nc.unc.dao.impl;

import com.nc.unc.dao.CustomerDao;
import com.nc.unc.util.jdbc.DBConnector;
import com.nc.unc.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {

    private static String GET_BY_ID =
            "select * from store.customer where id = ?;";

    private static final String UPDATE_CUSTOMER_TEMPLATE =
            "update store.customer " +
                    "set first_name = ?, last_name = ?, phone_number = ?, date = ? " +
                    "where id = ?;";

    private static Logger log = LoggerFactory.getLogger(CustomerDaoImpl.class.getSimpleName());

    private final static String GET_ALL_CUSTOMER =
            "select * from store.customer;";

    private final static String INSERT_CUSTOMER_TEMPLATE =
            "insert into " +
                    "store.customer(first_name, last_name, phone_number, date) " +
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

    public void update(Customer customer, Integer id){
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER_TEMPLATE)) {
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setDate(4, Date.valueOf(customer.getData()));
            statement.setInt(5, id);
            statement.executeUpdate();
        }catch (SQLException exc){
            log.info("Exception update customer {}", customer, exc);
        }
    }

    public void insert(Customer customer){
        try(Connection connection = DBConnector.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_TEMPLATE)) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setDate(4, Date.valueOf(customer.getData()));

            preparedStatement.executeUpdate();
        }catch (SQLException exc){
            log.info("Insert into customer exception", exc);
        }
    }

    @Override
    public Optional<Customer> getByKey(Integer id) {
        Customer customer = null;
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) customer = customerMapper(rs);
        }catch (SQLException exc){
            log.info("Get all customer with conditions {} exception", id, exc);
        }
        return Optional.ofNullable(customer);
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
