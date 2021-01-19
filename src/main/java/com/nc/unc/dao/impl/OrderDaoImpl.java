package com.nc.unc.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.nc.unc.dao.DBConnector;
import com.nc.unc.dao.Dao;
import com.nc.unc.enums.StatusOrder;
import com.nc.unc.model.Address;
import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.model.OrderItem;
import com.nc.unc.util.json.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class OrderDaoImpl implements Dao<Integer, Order> {

    private static final String GET_ALL_ORDERS =
            "select s.id sender_id, s.zipcode sender_zipcode, s.address sender_address, " +
                    " c.id customer_id, c.date customer_date, c.last_name customer_ls, c.first_name customer_fn, c.phone_number customer_pn, " +
                    " r.id recipient_id, r.zipcode recipient_zipcode, r.address recipient_address, " +
                    " o.id, o.sent_when, o.created_when, o.status, o.sum, o.status, o.order_items " +
                    " from store.order as o " +
                    " join store.address as r on r.id = o.recipient" +
                    " join store.address as s on s.id = o.sender" +
                    " join store.customer as c on c.id = o.customer" ;

    private final Logger logger = LoggerFactory.getLogger(AddressDaoImpl.class.getSimpleName());

    private static final String INSERT_ORDER_TEMPLATE = "insert into store.\"order\" " +
            "(customer, recipient, sender, order_items, created_when, sum) "+
            "values (?,?,?, to_json(?::json),?,?)";

    @Override
    public Map<Integer, Order> getAll() {
        Map<Integer, Order> orders = new HashMap<>();
        try(Connection connection = DBConnector.connection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(GET_ALL_ORDERS);
            while (rs.next())
                orders.put(rs.getInt("id"), customerMapper(rs));
        }catch (SQLException exc){
            logger.error("Error get all objects into DB", exc);
        }
        return orders;
    }

    @Override
    public Map<Integer, Order> gelAllWhere(String str) {
        return null;
    }

    public void insert(int customerId, int recipientId, int senderId,
                       String storage, LocalDate createdWhen, float sum){
        try(Connection connection = DBConnector.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_TEMPLATE)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, recipientId);
            preparedStatement.setInt(3, senderId);
            preparedStatement.setObject(4, storage);
            preparedStatement.setDate(5, Date.valueOf(createdWhen));
            preparedStatement.setFloat(6, sum);

            preparedStatement.executeUpdate();
        }catch (SQLException exc){
            logger.error("Insert into order table exception", exc);
        }
    }

    private static Order customerMapper(ResultSet rs) throws SQLException {
        return Order.builder()
                .key(rs.getInt("id"))
                .recipient(Address.builder()
                        .key(rs.getInt("recipient_id"))
                        .address(rs.getString("recipient_address"))
                        .zipCode(rs.getInt("recipient_zipcode"))
                        .build())
                .customer(Customer.builder()
                        .key(rs.getInt("customer_id"))
                        .lastName(rs.getString("customer_ls"))
                        .firstName(rs.getString("customer_fn"))
                        .data(rs.getDate("customer_date").toLocalDate())
                        .build())
                .sender(Address.builder()
                        .key(rs.getInt("sender_id"))
                        .address(rs.getString("sender_address"))
                        .zipCode(rs.getInt("sender_zipcode"))
                        .build())
                .products(JsonHelper.fromJson(rs.getString("order_items"), new TypeReference<>() {}))
                .createdWhen(rs.getDate("created_when").toLocalDate())
                .sentWhen(Optional.ofNullable(rs.getDate("sent_when"))
                        .filter(Objects::nonNull).map(Date::toLocalDate)
                        .orElse(null))
                .sum(rs.getFloat("sum"))
                .curStatusOrder((StatusOrder) rs.getObject("status"))
                .build();

    }
}
