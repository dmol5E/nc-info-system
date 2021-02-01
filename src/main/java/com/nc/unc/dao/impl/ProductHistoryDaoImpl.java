package com.nc.unc.dao.impl;

import com.nc.unc.dao.ProductHistoryDao;
import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;
import com.nc.unc.model.ProductHistory;
import com.nc.unc.util.jdbc.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProductHistoryDaoImpl implements ProductHistoryDao {

    private Logger log = LoggerFactory.getLogger(ProductHistoryDaoImpl.class.getSimpleName());

    private OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl();

    @Override
    public Optional<ProductHistory> search(OrderItem orderItem) {
        return getProductHistory(orderItem.getName(), orderItem.getPrice());
    }

    @Override
    public Optional<ProductHistory> search(Product product) {
        return getProductHistory(product.getName(), product.getPrice());
    }

    private Optional<ProductHistory> getProductHistory(String name, float price) {
        String sqlSearchProduct = "select * from store.product_history where name = ? and price = ?";
        ProductHistory productHistory = null;
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sqlSearchProduct))  {
            statement.setString(1, name);
            statement.setFloat(2, price);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) productHistory = mapperProductHistory(rs);
        }catch (SQLException exc){
            log.info("Search product history exception");
        }

        return Optional.ofNullable(productHistory);
    }



    @Override
    public void update(ProductHistory value, Integer key) {
        String sqlUpdateProductHistory =
                "update store.product_history" +
                " set name = ?, price = ?;  ";

        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sqlUpdateProductHistory)) {
            statement.setInt(1, value.getKey());
            statement.setString(2, value.getName());
            statement.setFloat(3, value.getPrice());
            statement.executeUpdate();
        }catch (SQLException exc){
            log.info("Update productHistory exception");
        }
    }

    @Override
    public Map<Integer, ProductHistory> getAll() {
        String sqlGetAll = "select * from store.product_history;";
        Map<Integer, ProductHistory> productHistories = new HashMap<>();

        try(Connection connection = DBConnector.connection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sqlGetAll);
            while (rs.next()) productHistories.put(rs.getInt("id"), mapperProductHistory(rs));
        }catch (SQLException exc){
            log.info("Update productHistory exception");
        }

        return productHistories;
    }

    @Override
    public int insert(ProductHistory t) {
        String sqlInsert =
                "insert into store.product_history (name, price) " +
                        "values (?,?)";
        int key = 0;
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, t.getName());
            statement.setFloat(2, t.getPrice());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) key = rs.getInt(1);
        } catch (SQLException exc){
            log.info("Insert into product history exception {}", t, exc);
        }
        return key;
    }

    @Override
    public Optional<ProductHistory> getByKey(Integer id) {
        String sqlGetByKey = "select * from store.product_history where id = ?";
        ProductHistory productHistory = null;
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sqlGetByKey)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) productHistory = mapperProductHistory(rs);
        }catch (SQLException exc){
            log.info("Get by key product history {}", id, exc);
        }

        return Optional.ofNullable(productHistory);
    }

    private ProductHistory mapperProductHistory(ResultSet rs) throws SQLException {
        return ProductHistory.builder()
                .key(rs.getInt("id"))
                .name(rs.getString("name"))
                .price(rs.getFloat("price"))
                .orderItem(orderItemDao.getByProductHistory(rs.getInt("id")))
                .build();
    }
}
