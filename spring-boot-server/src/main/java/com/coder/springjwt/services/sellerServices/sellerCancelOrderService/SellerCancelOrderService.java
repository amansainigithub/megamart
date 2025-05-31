package com.coder.springjwt.services.sellerServices.sellerCancelOrderService;

import com.coder.springjwt.dtos.sellerDtos.refundDtos.RefundRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerCancelOrderService {
    ResponseEntity<?> sellerCancelOrdersFetch(Integer page, Integer size);

    ResponseEntity<?> sellerCancelOrdersFetchPaymentComplete(Integer page, Integer size);


    ResponseEntity<?> sellerOrderRefundRequest(RefundRequestDto refundRequestDto);

    ResponseEntity<?> sellerCancelOrders(long id);

}
