package com.nc.unc.myDao;

import com.nc.unc.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao extends CrudDAO<Order> {

    List<Order> searchOrderByPeriod(LocalDate firstDate,
                                    LocalDate secondDate);
}
