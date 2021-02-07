package com.nc.unc.myDao.mapper;

import com.nc.unc.model.OrderItem;
import com.nc.unc.myDao.template.EntityImpl;
import org.springframework.stereotype.Component;

@Component
public class OrderItemDaoMapper extends AbstractMapper<OrderItem> {

    public OrderItemDaoMapper() {
        super(OrderItem.class, new EntityImpl<>(OrderItem.class));
    }


}
