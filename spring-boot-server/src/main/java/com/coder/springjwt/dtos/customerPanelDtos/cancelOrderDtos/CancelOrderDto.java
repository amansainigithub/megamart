package com.coder.springjwt.dtos.customerPanelDtos.cancelOrderDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderDto {

    private String id;

    private String customerOrderNumber;

    private String razorpayOrderId;
}
