package com.coder.springjwt.services.PaymentsServices.razorpay;

import com.coder.springjwt.payload.paymentTransaction.PaymentTransactionPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface RazorpayServices {
    ResponseEntity<?> createOrder(Double amount );

    ResponseEntity<?> orderUpdate(PaymentTransactionPayload paymentTransactionPayload);
}
