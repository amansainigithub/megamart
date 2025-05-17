package com.coder.springjwt.services.publicService.orderServices;

import com.coder.springjwt.dtos.customerPanelDtos.returnExchangeDto.ExchangeRequestDto;
import com.coder.springjwt.dtos.customerPanelDtos.returnExchangeDto.ReturnRequestInitiateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrderService {
    ResponseEntity<?> getCustomerOrders(long id, Integer page, Integer size);

    ResponseEntity<?> getMyOrdersDelivered(long id , Integer page, Integer size);

    ResponseEntity<?> getCustomerOrdersById(long id);


    ResponseEntity<?> orderReturnRequestInitiate(ReturnRequestInitiateDto returnRequestInitiateDto);

    ResponseEntity<?> orderExchangeRequestInitiate( ExchangeRequestDto exchangeRequestDto);

}
