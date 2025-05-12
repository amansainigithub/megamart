package com.coder.springjwt.services.sellerServices.returnOrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ReturnExchnageOrderService {

    ResponseEntity<?> exchangeDeliveryStatus(Long id,String exchangeDeliveryStatus);

    ResponseEntity<?> exchangePickupDateTime(Long id, String pickupDateTime);
}
