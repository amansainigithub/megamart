package com.coder.springjwt.services.publicService.orderServices;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrderService {
    ResponseEntity<?> getCustomerOrders(long id);

    ResponseEntity<?> getCustomerOrdersById(long id);
}
