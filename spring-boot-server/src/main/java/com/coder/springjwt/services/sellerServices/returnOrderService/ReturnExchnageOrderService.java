package com.coder.springjwt.services.sellerServices.returnOrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ReturnExchnageOrderService {
    ResponseEntity<?> returnPaymentInitiated(Long id);

    ResponseEntity<?> changeReturnDeliveryStatus(Long id, String returnDeliveryStatus);

    ResponseEntity<?> changeReturnPickUpDateTime(Long id, String pickupDateTime);

    ResponseEntity<?> exchangeDeliveryStatus(Long id,String exchangeDeliveryStatus);

    ResponseEntity<?> exchangePickupDateTime(Long id, String pickupDateTime);
}
