package com.nc.unc.dao.impl;

import com.nc.unc.util.jdbc.DBConnector;
import com.nc.unc.dao.Dao;
import com.nc.unc.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProductDaoImpl implements Dao<Integer, Product> {

    private static final Logger log = LoggerFactory.getLogger(ProductDaoImpl.class.getSimpleName());

    private static final String GET_ALL_PRODUCT =
            "select * from store.product;";

    private static final String INSERT_PRODUCT_TEMPLATE =
            "insert into store.product (name, price, count) " +
                    "values (?,?,?);";

    private static final String UPDATE_ORDER_TEMPLATE =
            "update store.product " +
                    "set name = ?, price = ?, count = ? " +
                    "where id = ?";

    public void update(Product product, Integer id) {
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_TEMPLATE)) {
            statement.setString(1, product.getName());
            statement.setFloat(2, product.getPrice());
            statement.setInt(3, product.getCount());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException exc){
            log.info("Exception update product {}", product, exc);
        }
    }

    @Override
    public Map<Integer, Product> getAll() {
        Map<Integer, Product> products = new HashMap<>();
        try(Connection connection = DBConnector.connection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(GET_ALL_PRODUCT);
            while (rs.next())
                products.put(rs.getInt("id"), productMapper(rs));
        }catch (SQLException exc){
            log.info("Get all products from DB exception", exc);
        }
        return products;
    }

    @Override
    public void insert(Product product){
        try(Connection connection = DBConnector.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_TEMPLATE)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getPrice());
            preparedStatement.setInt(3, product.getCount());
            preparedStatement.executeUpdate();
        }catch (SQLException exc){
            log.info("Insert into products table exception", exc);
        }
    }

    @Override
    public Optional<Product> getByKey(Integer id) {
        Product product = null;
        String SELECT_BY_ID = "select * from store.product where id = ?; ";
        try(Connection connection = DBConnector.connection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) product = productMapper(rs);
        }catch (SQLException exc){
            log.info("Get all product by id {} exception", id, exc);
        }

        return Optional.ofNullable(product);
    }

    private static Product productMapper(ResultSet rs)throws SQLException{
        return Product.builder()
                .key(rs.getInt("id"))
                .count(rs.getInt("count"))
                .name(rs.getString("name"))
                .price(rs.getFloat("price"))
                .build();
    }
}
