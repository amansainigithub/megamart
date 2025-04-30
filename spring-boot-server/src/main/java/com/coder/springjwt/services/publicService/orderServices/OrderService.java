package com.coder.springjwt.services.publicService.orderServices;

import com.coder.springjwt.dtos.customerPanelDtos.returnExchangeDto.ExchangeRequestDto;
import com.coder.springjwt.dtos.customerPanelDtos.returnExchangeDto.ReturnRequestInitiateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrderService {
    ResponseEntity<?> getCustomerOrders(long id);

    ResponseEntity<?> getMyOrdersDelivered(long id);

    ResponseEntity<?> getCustomerOrdersById(long id);


    ResponseEntity<?> orderReturnRequestInitiate(ReturnRequestInitiateDto returnRequestInitiateDto);

    ResponseEntity<?> orderExchangeRequestInitiate( ExchangeRequestDto exchangeRequestDto);

}
