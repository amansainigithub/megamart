package com.coder.springjwt.payload.paymentTransaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTransactionPayload {

    private String razorpay_order_id;
    private String razorpay_payment_id;
    private String razorpay_signature;


}
