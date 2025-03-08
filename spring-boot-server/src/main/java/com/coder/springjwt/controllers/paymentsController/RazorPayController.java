package com.coder.springjwt.controllers.paymentsController;

import com.coder.springjwt.constants.customerConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.payload.paymentTransaction.PaymentTransactionPayload;
import com.coder.springjwt.services.PaymentsServices.razorpay.RazorpayServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerUrlMappings.PAYMENT_CONTROLLER)
public class RazorPayController {
    @Autowired
    private RazorpayServices razorpayServices;

    @GetMapping(CustomerUrlMappings.CREATE_ORDER)
    public ResponseEntity<?> createOrder(@RequestParam Double amount) {
        return this.razorpayServices.createOrder(amount);
    }

    @PostMapping(CustomerUrlMappings.ORDER_UPDATE)
    public ResponseEntity<?> orderUpdate(@RequestBody PaymentTransactionPayload paymentTransactionPayload) {
        return this.razorpayServices.orderUpdate(paymentTransactionPayload);
    }





}
