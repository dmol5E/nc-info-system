package com.nc.unc.dao.impl;

import com.nc.unc.dao.DBConnector;
import com.nc.unc.dao.Dao;
import com.nc.unc.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ProductDaoImpl implements Dao<Integer, Product> {

    private static final Logger log = LoggerFactory.getLogger(ProductDaoImpl.class.getSimpleName());

    private static final String GET_ALL_PRODUCT = "select * from store.product;";

    private static final String INSERT_PRODUCT_TEMPLATE = "insert into store.product (name, price, count) "
            + "values (?,?,?);";

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
    public Map<Integer, Product> gelAllWhere(String str) {
        return null;
    }

    public void insert(String name, float price, int count){
        try(Connection connection = DBConnector.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_TEMPLATE)) {
            preparedStatement.setString(1, name);
            preparedStatement.setFloat(2, price);
            preparedStatement.setInt(3, count);

            preparedStatement.executeUpdate();
        }catch (SQLException exc){
            log.info("Insert into products table exception", exc);
        }
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
