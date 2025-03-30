package com.coder.springjwt.services.sellerServices.deliveryStatusService;

import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface DeliveryStatusService {


        ResponseEntity<?> updateDeliveryStatusOrderItems(DeliveryStatusDto deliveryStatusDto);

        ResponseEntity<?> getDeliveryDetailsById(Long id);
}
