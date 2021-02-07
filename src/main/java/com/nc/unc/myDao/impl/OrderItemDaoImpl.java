package com.nc.unc.myDao.impl;

import com.nc.unc.model.OrderItem;
import com.nc.unc.myDao.mapper.OrderItemDaoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@PropertySource("classpath:db/sql/OrderItemSQL.properties")
@Repository
public class OrderItemDaoImpl extends CrudDaoImpl<OrderItem> implements com.nc.unc.myDao.OrderItemDao {

    @Value("${SELECT_ALL_BY_ORDER_ID}")
    private String SELECT_ALL_BY_ORDER_ID;

    @Value("${SELECT_ALL_BY_PRODUCT_HISTORY_ID}")
    private String SELECT_ALL_BY_PRODUCT_HISTORY_ID;

    @Value("${SEARCH_BY_ORDER_ITEM}")
    private String SEARCH_BY_ORDER_ITEM;

    @Override
    public List<OrderItem> findAllByIdOrder(Number id) {
        log.debug("SQL {} \n Find all by id order {}", SELECT_ALL_BY_ORDER_ID, id);
        return getJdbcTemplate().query(SELECT_ALL_BY_ORDER_ID,
                new Object[]{id},
                getAbstractMapper());
    }

    @Override
    public List<OrderItem> getByProductHistory(Number id) {
        log.debug("SQL {} \n Find by productHistory {}", SELECT_ALL_BY_PRODUCT_HISTORY_ID, id);
        return getJdbcTemplate().query(SELECT_ALL_BY_PRODUCT_HISTORY_ID,
                new Object[]{id},
                getAbstractMapper());
    }

    @Override
    public Optional<OrderItem> search(String name, Long price, int count) {
        log.debug("SQL {} \n search by name {} price {} count {}",
                SEARCH_BY_ORDER_ITEM, name, price, count);
        return Optional.ofNullable(getJdbcTemplate().
                queryForObject(SEARCH_BY_ORDER_ITEM,
                        new Object[]{name, price, count},
                        getAbstractMapper()));
    }

    @Autowired
    public void setAbstractMapper(OrderItemDaoMapper abstractMapper) {
        super.setAbstractMapper(abstractMapper);
    }
}
