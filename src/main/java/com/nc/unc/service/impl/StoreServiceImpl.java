package com.nc.unc.service.impl;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.dto.ProductDto;
import com.nc.unc.exception.RequestException;
import com.nc.unc.model.Product;
import com.nc.unc.model.ProductHistory;
import com.nc.unc.dao.IProductDao;
import com.nc.unc.dao.IProductHistoryDao;
import com.nc.unc.service.IStoreService;
import com.nc.unc.service.mapper.impl.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StoreServiceImpl implements IStoreService {

    private IProductHistoryDao IProductHistoryDao;
    private IProductDao IProductDao;
    private ProductMapper modelMapper;

    @Autowired
    public StoreServiceImpl(IProductDao IProductDao,
                            IProductHistoryDao IProductHistoryDao,
                            ProductMapper modelMapper){
        this.modelMapper = modelMapper;
        this.IProductDao = IProductDao;
        this.IProductHistoryDao = IProductHistoryDao;
    }


    @Override
    public List<ProductDto> getAll() {
        log.debug("StoreServiceImpl.getAll() was invoked");
        return IProductDao.getAll()
                .stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(int productId) {
        log.debug("StoreServiceImpl.findById(int id) was invoked");
        Optional<Product> optionalProduct = IProductDao.find(productId);
        if(optionalProduct.isEmpty()){
            log.error("No such product with id {}", productId);
            throw new RequestException("No such product", HttpStatus.NOT_FOUND);
        }
        return optionalProduct.stream()
                .map(modelMapper::toDto)
                .findFirst()
                .get();
    }

    @Override
    public ProductDto increase(int productId, int increaseProductCount) {
        log.debug("StoreServiceImpl.increase(RequestToIncreaseProducts requestToIncreaseProducts) was invoked");

        if(increaseProductCount < 0) {
            log.error("Negative count {} by id {}", increaseProductCount, productId);
            throw new RequestException("Negative count to increase product", HttpStatus.BAD_REQUEST);
        }

        return changeProductCount(productId, increaseProductCount);
    }

    @Override
    public ProductDto decrease(int productId, int decreaseProductCount) {
        log.debug("StoreServiceImpl.increase(RequestToIncreaseProducts requestToIncreaseProducts) was invoked");

        if(decreaseProductCount < 0) {
            log.error("Negative count {} by id {}", decreaseProductCount, productId);
            throw new RequestException("Negative count to increase product", HttpStatus.BAD_REQUEST);
        }

        return changeProductCount(productId,(-1) * decreaseProductCount);
    }

    private ProductDto changeProductCount(int productId, int newCount){
        log.debug("StoreServiceImpl.changeProductCount(int productId, int newCount) was invoked");
        Optional<Product> optionalProduct = IProductDao.find(productId);

        if(optionalProduct.isEmpty()) {
            log.error("No such product with id {}", productId);
            throw new RequestException("No such product", HttpStatus.BAD_REQUEST);
        }

        optionalProduct.get().setCount(optionalProduct.get().getCount() + newCount);
        IProductDao.update(optionalProduct.get());

        return optionalProduct.stream()
                .map(modelMapper::toDto)
                .findFirst()
                .get();
    }

    @Override
    public void put(ProductDto productDto) {
        log.debug("StoreServiceImpl.put(ProductDto productDto) was invoked");

        if(productDto.getCount() < 0 || productDto.getPrice() < 0) {
            log.error("Invalid date from productDto count : {} price: {} ", productDto.getCount(), productDto.getPrice());
            throw new RequestException("Invalid date from productDto", HttpStatus.BAD_REQUEST);
        }
        IProductHistoryDao.insert(ProductHistory.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build());
        IProductDao.insert(modelMapper.toEntity(productDto));
    }

    @Override
    public ProductDto search(OrderItemDto orderItemDto) {
        log.debug("StoreServiceImpl.search(OrderItemDto orderItemDto) was invoked");
        Optional<Product> product = IProductDao.search(orderItemDto);
        if(product.isEmpty()) {
            log.error("No such element ");
            throw new RequestException("No such element ", HttpStatus.BAD_REQUEST);
        }
        return IProductDao.search(orderItemDto).stream().map(modelMapper::toDto).findFirst().get();
    }
}
