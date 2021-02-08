package com.nc.unc.myService.impl;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.dto.ProductDto;
import com.nc.unc.exception.RequestException;
import com.nc.unc.model.OrderItem;
import com.nc.unc.myDao.OrderItemDao;
import com.nc.unc.myService.IProductHistoryService;
import com.nc.unc.myService.IStorageService;
import com.nc.unc.myService.IStoreService;
import com.nc.unc.myService.mapper.OrderItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Slf4j
@Service
@Scope("session")
public class StorageService implements IStorageService {

    private HashMap<Integer, OrderItemDto> storage = new HashMap<>();
    private OrderItemMapper orderItemMapper;
    private OrderItemDao orderItemDao;
    private IProductHistoryService productHistoryService;
    @Autowired
    public StorageService(IStoreService storeService,
                          OrderItemDao orderItemDao,
                          OrderItemMapper orderItemMapper,
                          IProductHistoryService productHistoryService){
        this.orderItemMapper = orderItemMapper;
        this.orderItemDao = orderItemDao;
        this.productHistoryService = productHistoryService;
    }

    @Override
    public void formAnOrder(int id) {
        log.debug("StorageService.formAnOrder(int id) was invoked");
        storage.values().forEach(orderItemDto -> {
            OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
            orderItem.setOrderId(id);
            orderItem.setProductHistoryId(productHistoryService.searchByOrderItem(orderItemDto.getName(), orderItemDto.getPrice()).getId());
            orderItemDao.insert(orderItem);
        });
    }

    @Override
    public void putOrderItem(ProductDto product, int increase) {
        log.debug("StorageService.putOrderItem(ProductDto product, int increase) was invoked");
        if(increase < 0 || product.getCount() < increase){
            log.error("Illegal date");
            throw new RequestException("Illegal date", HttpStatus.BAD_REQUEST);
        }

        OrderItemDto orderItem = storage.values().stream()
                .filter(storageItem -> product.getName().equals(storageItem.getName()))
                .filter(storageItem -> product.getPrice() == product.getPrice())
                .findFirst().orElse(null);
        if(orderItem != null){
            if(orderItem.getCount() + increase > product.getCount()) {
                log.error("No such products");
                throw new RequestException("Illegal date", HttpStatus.BAD_REQUEST);
            }

            orderItem.setCount(orderItem.getCount() + increase);

        } else {

            OrderItemDto newStorageItem = OrderItemDto.builder()
                    .id(this.size())
                    .name(product.getName())
                    .price(product.getPrice())
                    .count(increase)
                    .build();
            storage.put(newStorageItem.getId(), newStorageItem);

        }
    }

    @Override
    public int size() {
        log.debug("StorageService.size() was invoked");
        return storage.size();
    }

    @Override
    public float getPrice() {
        log.debug("StorageService.getPrice() was invoked");
        return storage.values().stream()
                .reduce(0.f,
                        (x, y) -> x + y.getCount() * y.getPrice(),
                        Float::sum);
    }

    @Override
    public void removeOrderItem(int id) {
        log.debug("StorageService.removeOrderItem(int id) was invoked");
        storage.remove(id);
    }

    @Override
    public List<OrderItemDto> get() {
        log.debug("StorageService.get() was invoked");
        return new ArrayList<>(storage.values());
    }
}
