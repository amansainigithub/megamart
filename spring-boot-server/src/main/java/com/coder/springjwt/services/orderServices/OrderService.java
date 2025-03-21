package com.coder.springjwt.services.orderServices;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrderService {
    ResponseEntity<?> getCustomerOrders(long id);
}
