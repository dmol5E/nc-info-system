package com.nc.unc.dao;

import com.nc.unc.model.ProductHistory;

import java.util.Optional;

public interface IProductHistoryDao extends ICrudDAO<ProductHistory> {
    Optional<ProductHistory> searchOrderItem(String name, float price);
    Optional<ProductHistory> searchProduct(String name, float price);
}
