package com.nc.unc.dao.impl;

import com.nc.unc.dao.OrderDao;
import com.nc.unc.util.jdbc.DBConnector;
import com.nc.unc.enums.StatusOrder;
import com.nc.unc.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class OrderDaoImpl implements OrderDao {

    private static final String GET_BY_ID =
            "select s.id sender_id, s.zipcode sender_zipcode, s.address sender_address, " +
                    " c.id customer_id, c.date customer_date, c.last_name customer_ls, c.first_name customer_fn, c.phone_number customer_pn, " +
                    " r.id recipient_id, r.zipcode recipient_zipcode, r.address recipient_address, " +
                    " o.id, o.sent_when, o.created_when, o.status, o.sum, o.status " +
                    " from store.order as o " +
                    " join store.address as r on r.id = o.recipient" +
                    " join store.address as s on s.id = o.sender" +
                    " join store.customer as c on c.id = o.customer where o.id = ?; " ;

    private static final String GET_ALL_ORDERS =
            "select s.id sender_id, s.zipcode sender_zipcode, s.address sender_address, " +
                    " c.id customer_id, c.date customer_date, c.last_name customer_ls, c.first_name customer_fn, c.phone_number customer_pn, " +
                    " r.id recipient_id, r.zipcode recipient_zipcode, r.address recipient_address, " +
                    " o.id, o.sent_when, o.created_when, o.status, o.sum, o.status " +
                    " from store.order as o " +
                    " join store.order_item as items on o.id = items.order_id" +
                    " join store.address as r on r.id = o.recipient" +
                    " join store.address as s on s.id = o.sender" +
                    " join store.customer as c on c.id = o.customer; " ;

    private static final String UPDATE_ORDER_TEMPLATE = "update store.order " +
            "set customer = ?, recipient = ?, sender = ?, " +
            "created_when = ?," + "sent_when = ?, sum = ?, status = ? " +
            "where id = ?; ";

    private static final String INSERT_ORDER_TEMPLATE =
            "insert into store.order " +
                    "(customer, recipient, sender, created_when, sum) "+
                    "values (?,?,?,?,?); ";
    private static final OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl();

    private final Logger logger = LoggerFactory.getLogger(AddressDaoImpl.class.getSimpleName());

    public void update(Order order, Integer id) {
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_TEMPLATE)) {
            statement.setInt(1, order.getCustomer().getKey());
            statement.setInt(2, order.getRecipient().getKey());
            statement.setInt(3, order.getSender().getKey());
            statement.setDate(4, Date.valueOf(order.getCreatedWhen()));
            statement.setDate(5, Optional.ofNullable(order.getSentWhen())
                    .filter(Objects::nonNull)
                    .map(Date::valueOf)
                    .orElse(null));
            statement.setFloat(6, order.getSum());
            statement.setObject(7, order.getCurStatusOrder(), Types.OTHER);
            statement.setInt(8, id);
            statement.executeUpdate();
        }catch (SQLException exc){
            logger.info("Update order exception {} ", order, exc);
        }
    }

    @Override
    public Map<Integer, Order> getAll() {
        Map<Integer, Order> orders = new HashMap<>();
        try(Connection connection = DBConnector.connection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(GET_ALL_ORDERS);
            while (rs.next())
                orders.put(rs.getInt("id"), orderMapper(rs));
        }catch (SQLException exc){
            logger.error("Error get all objects into DB", exc);
        }
        return orders;
    }

    public int insert(Order order){
        int key = 0;
        try(Connection connection = DBConnector.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_TEMPLATE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, order.getCustomer().getKey());
            preparedStatement.setInt(2, order.getRecipient().getKey());
            preparedStatement.setInt(3, order.getSender().getKey());
            preparedStatement.setDate(4, Date.valueOf(order.getCreatedWhen()));
            preparedStatement.setFloat(5, order.getSum());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) key = rs.getInt(1);
        }catch (SQLException exc){
            logger.error("Insert into order table exception", exc);
        }
        return key;
    }

    @Override
    public Optional<Order> getByKey(Integer id) {
        Order order = null;
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) order = orderMapper(rs);
        }catch (SQLException exc){
            logger.info("Get all orders by id {} exception", id, exc);
        }
        return Optional.ofNullable(order);
    }

    private static Order orderMapper(ResultSet rs) throws SQLException {
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
                        .phoneNumber(rs.getString("customer_pn"))
                        .build())
                .sender(Address.builder()
                        .key(rs.getInt("sender_id"))
                        .address(rs.getString("sender_address"))
                        .zipCode(rs.getInt("sender_zipcode"))
                        .build())
                .products(orderItemDao.getByOrderId(rs.getInt("id")))
                .createdWhen(rs.getDate("created_when").toLocalDate())
                .sentWhen(Optional.ofNullable(rs.getDate("sent_when"))
                        .filter(Objects::nonNull).map(Date::toLocalDate)
                        .orElse(null))
                .sum(rs.getFloat("sum"))
                .curStatusOrder(StatusOrder.valueOf(rs.getString("status")))
                .build();

    }
}
