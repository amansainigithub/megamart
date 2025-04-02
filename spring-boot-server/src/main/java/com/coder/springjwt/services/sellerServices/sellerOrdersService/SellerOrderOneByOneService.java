package com.coder.springjwt.services.sellerServices.sellerOrdersService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerOrderOneByOneService {
    ResponseEntity<?> getPendingOrderOneByOneBySeller(Integer page, Integer size);

    ResponseEntity<?> getShippedOrderOneByOneBySeller(Integer page, Integer size);

    ResponseEntity<?> getOutOfDeliveryOrderOneByOneBySeller(Integer page, Integer size);

    ResponseEntity<?> getDeliveredOrderOneByOneBySeller(Integer page, Integer size);
}
