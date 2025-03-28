package com.coder.springjwt.payload.customerPayloads.paymentTransaction;

import com.coder.springjwt.dtos.customerPanelDtos.cartItemsDto.CartItemsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTransactionPayload {

    private String razorpay_order_id;
    private String razorpay_payment_id;
    private String razorpay_signature;

    private List<CartItemsDto> cartItems;


}
