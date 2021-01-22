package com.nc.unc.dao.impl;

import com.nc.unc.dao.OrderItemDao;
import com.nc.unc.model.OrderItem;
import com.nc.unc.util.jdbc.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderItemDaoImpl implements OrderItemDao {



    private static Logger log = LoggerFactory.getLogger(OrderItemDaoImpl.class.getSimpleName());

    @Override
    public Map<Integer, OrderItem> getByProductHistory(int id){
        String sql = "select * from store.order_item where product_history_id = ?;";

        Map<Integer, OrderItem> orderItems = new HashMap<>();

        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) orderItems.put(rs.getInt("id"), orderItemMapper(rs));
        }catch (SQLException exc){
            log.info("Get all order item by id{}", id, exc);
        }
        return orderItems;
    }

    @Override
    public Map<Integer, OrderItem> getByOrderId(int id) {
        String sql = "select * from store.order_item where order_id = ?; ";
        Map<Integer, OrderItem> orderItems = new HashMap<>();
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) orderItems.put(rs.getInt("id"), orderItemMapper(rs));
        }catch (SQLException exc){
            log.info("Get by order id exc {}", id, exc);
        }
        return orderItems;
    }


    @Override
    public Optional<OrderItem> search(OrderItem orderItem) {
        String sqlSearch = "select * from store.order_item" +
                " where name = ? and price = ? and count = ?;";

        OrderItem orderItemDB = null;
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sqlSearch)) {
            statement.setString(1, orderItem.getName());
            statement.setFloat(2, orderItem.getPrice());
            statement.setInt(3, orderItem.getCount());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) orderItemDB = orderItemMapper(rs);
        }catch (SQLException exc) {
            log.info("Exception search orderItem {}", orderItem, exc);
        }
        return Optional.ofNullable(orderItemDB);
    }

    @Override
    public void update(OrderItem value, Integer key) {
        String sqlUpdate
                = "update store.order_item " +
                " set name = ?, price = ?, count = ?, order_id = ?, product_history_id = ?" +
                " where id = ?;";

        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sqlUpdate)) {

            statement.setString(1, value.getName());
            statement.setFloat(2, value.getPrice());
            statement.setInt(3, value.getCount());

            statement.executeUpdate();
        }catch (SQLException exc) {
            log.info("Exception update orderItem {}", value, exc);
        }
    }

    @Override
    public Map<Integer, OrderItem> getAll() {
        String sqlGetAll = "select * from store.order_item;";

        Map<Integer, OrderItem> orderItems = new HashMap<>();
        try(Connection connection = DBConnector.connection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sqlGetAll);
            while (rs.next()) orderItems.put(rs.getInt("id"), orderItemMapper(rs));
        }catch (SQLException exc) {
            log.info("Exception find all orderItems", exc);
        }
        return orderItems;
    }

    @Override
    public void insert(OrderItem t, int productHistoryId, int orderId) {
        String sqlInsert
                = "insert into store.order_item(name, price, count, order_id, product_history_id)" +
                " values (?,?,?,?,?);";

        try(Connection connection = DBConnector.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, t.getName());
            preparedStatement.setFloat(2, t.getPrice());
            preparedStatement.setInt(3,t.getCount());
            preparedStatement.setInt(4,orderId);
            preparedStatement.setInt(5,productHistoryId);
            preparedStatement.executeUpdate();
        }catch (SQLException exc) {
            log.info("Exception insert orderItem {}", t, exc);
        }
    }

    @Override
    public Optional<OrderItem> getByKey(Integer id) {
        String sqlGetById = "select * from store.order_item where id = ?; ";
        OrderItem orderItem = null;
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sqlGetById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) orderItem = orderItemMapper(rs);
        }catch (SQLException exc) {
            log.info("Exception find by key orderItem {}", id, exc);
        }
        return Optional.ofNullable(orderItem);
    }

    public OrderItem orderItemMapper(ResultSet rs) throws SQLException {
        return OrderItem.builder()
                .key(rs.getInt("id"))
                .name(rs.getString("name"))
                .price(rs.getFloat("price"))
                .count(rs.getInt("count"))
                .build();
    }
}
