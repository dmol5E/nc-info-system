package com.nc.unc.dao.mapper;

import com.nc.unc.model.OrderItem;
import com.nc.unc.dao.template.EntityImpl;
import org.springframework.stereotype.Component;

@Component
public class OrderItemDaoMapper extends AbstractMapper<OrderItem> {

    public OrderItemDaoMapper() {
        super(OrderItem.class, new EntityImpl<>(OrderItem.class));
    }


}
