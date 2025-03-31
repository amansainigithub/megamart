package com.coder.springjwt.services.sellerServices.deliveryStatusService;

import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusDto;
import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface DeliveryStatusService {


        ResponseEntity<?> updatePendingDeliveryStatus(DeliveryStatusDto deliveryStatusDto);

        ResponseEntity<?> updateDeliveryStatus(DeliveryStatusUpdateDto deliveryStatusUpdateDto);

        ResponseEntity<?> getDeliveryDetailsById(Long id);


}
