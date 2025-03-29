package com.coder.springjwt.services.sellerServices.sellerOrdersService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerOrdersService {


    ResponseEntity<?> getOrderBySeller(Integer page, Integer page1);
}
