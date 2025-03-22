package com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class CustomerOrderDTO {

    private Long id;
    private String orderId;   // Razorpay Order ID
    private String paymentStatus;    // Success, Failed, Pending
    private String totalPrice;
    private String orderDateTime;;
    private int quantity;
    private String deliveryStatus;
    private List<CustomerOrderItemDTO> customerOrderItems;
}
