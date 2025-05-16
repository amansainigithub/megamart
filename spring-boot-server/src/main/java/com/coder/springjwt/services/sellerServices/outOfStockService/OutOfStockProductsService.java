package com.coder.springjwt.services.sellerServices.outOfStockService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface OutOfStockProductsService {

    ResponseEntity<?> getOutOfStockProductsService(Integer page, Integer size);

    ResponseEntity<?> updateOutOfStocksProducts(long id, String productInventory);
}
