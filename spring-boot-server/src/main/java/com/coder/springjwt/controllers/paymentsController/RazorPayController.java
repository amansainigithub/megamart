package com.coder.springjwt.controllers.paymentsController;

import com.coder.springjwt.constants.customerConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.dtos.cartItemsDto.CartItemsDto;
import com.coder.springjwt.payload.paymentTransaction.PaymentTransactionPayload;
import com.coder.springjwt.services.PaymentsServices.razorpay.RazorpayServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CustomerUrlMappings.PAYMENT_CONTROLLER)
public class RazorPayController {
    @Autowired
    private RazorpayServices razorpayServices;

    @PostMapping(CustomerUrlMappings.CREATE_ORDER)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createOrder(@RequestParam Double amount ,@RequestBody List<CartItemsDto> cartItems) {
        return this.razorpayServices.createOrder(amount, cartItems);
    }

    @PostMapping(CustomerUrlMappings.ORDER_UPDATE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> orderUpdate(@RequestBody PaymentTransactionPayload paymentTransactionPayload) {
        return this.razorpayServices.orderUpdate(paymentTransactionPayload);
    }





}
