package com.nc.unc.myService.impl;

import com.nc.unc.dto.OrderItemDto;
import com.nc.unc.dto.ProductDto;
import com.nc.unc.dto.request.RequestToIncreaseProducts;
import com.nc.unc.exception.RequestException;
import com.nc.unc.model.Product;
import com.nc.unc.model.ProductHistory;
import com.nc.unc.myDao.ProductDao;
import com.nc.unc.myDao.ProductHistoryDao;
import com.nc.unc.myService.IStoreService;
import com.nc.unc.myService.mapper.ProductMapper;
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

    private ProductHistoryDao productHistoryDao;
    private ProductDao productDao;
    private ProductMapper modelMapper;

    @Autowired
    public StoreServiceImpl(ProductDao productDao,
                            ProductHistoryDao productHistoryDao,
                            ProductMapper modelMapper){
        this.modelMapper = modelMapper;
        this.productDao = productDao;
        this.productHistoryDao = productHistoryDao;
    }


    @Override
    public List<ProductDto> getAll() {
        log.debug("StoreServiceImpl.getAll() was invoked");
        return productDao.getAll()
                .stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(int productId) {
        log.debug("StoreServiceImpl.findById(int id) was invoked");
        Optional<Product> optionalProduct = productDao.find(productId);
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
        Optional<Product> optionalProduct = productDao.find(productId);

        if(optionalProduct.isEmpty()) {
            log.error("No such product with id {}", productId);
            throw new RequestException("No such product", HttpStatus.BAD_REQUEST);
        }

        optionalProduct.get().setCount(optionalProduct.get().getCount() + newCount);
        productDao.update(optionalProduct.get());

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
        productHistoryDao.insert(ProductHistory.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build());
        productDao.insert(modelMapper.toEntity(productDto));
    }

    @Override
    public ProductDto search(OrderItemDto orderItemDto) {
        return null;
    }
}
