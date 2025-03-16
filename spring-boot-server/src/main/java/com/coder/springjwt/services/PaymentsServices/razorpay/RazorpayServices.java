package com.coder.springjwt.services.PaymentsServices.razorpay;

import com.coder.springjwt.dtos.customerPanelDtos.cartItemsDto.CartItemsDto;
import com.coder.springjwt.payload.customerPayloads.paymentTransaction.PaymentTransactionPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RazorpayServices {
    ResponseEntity<?> createOrder(Double amount ,  List<CartItemsDto> cartItems );

    ResponseEntity<?> orderUpdate(PaymentTransactionPayload paymentTransactionPayload);
}
