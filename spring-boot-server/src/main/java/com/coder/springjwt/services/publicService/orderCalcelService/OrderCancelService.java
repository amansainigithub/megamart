package com.coder.springjwt.services.publicService.orderCalcelService;

import com.coder.springjwt.dtos.customerPanelDtos.cancelOrderDtos.CancelOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrderCancelService {

    ResponseEntity<?> orderCancelService(CancelOrderDto cancelOrderDto);
}
