package com.nc.unc.dao;

import com.nc.unc.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface IOrderDao extends ICrudDAO<Order> {

    List<Order> searchOrderByPeriod(LocalDate firstDate,
                                    LocalDate secondDate);
}
