package com.nc.unc.dao.impl;

import com.nc.unc.model.Order;
import com.nc.unc.dao.IOrderDao;
import com.nc.unc.dao.mapper.OrderDaoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
@PropertySource("classpath:db/sql/OrderSQL.properties")
public class IOrderDaoImpl extends CrudDaoImpl<Order> implements IOrderDao {
    @Value("${SELECT_ORDER_BY_PERIOD}")
    private String SELECT_ORDER_BY_PERIOD;

    @Override
    public List<Order> searchOrderByPeriod(LocalDate firstDate, LocalDate secondDate) {
        log.debug("SQL {} search Order by period: {} - {} ",
                SELECT_ORDER_BY_PERIOD, firstDate, secondDate);
        return getJdbcTemplate().query(SELECT_ORDER_BY_PERIOD,
                new Object[]{firstDate, secondDate},
                getAbstractMapper());
    }

    @Autowired
    public void setAbstractMapper(OrderDaoMapper orderDaoMapper) {
        super.setAbstractMapper(orderDaoMapper);
    }

}
