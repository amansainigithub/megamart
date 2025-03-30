package com.coder.springjwt.services.sellerServices.sellerOrdersService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerOrdersService {


    ResponseEntity<?> getPendingOrderBySeller(Integer page, Integer page1);

    ResponseEntity<?> getShippedOrderBySeller(Integer page, Integer size);

    ResponseEntity<?> getOutOfDeliveryOrderBySellerService(Integer page, Integer size);

    ResponseEntity<?> getDeliveredOrderBySellerService(Integer page, Integer size);
}
