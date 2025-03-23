package com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos;

import com.coder.springjwt.models.customerPanelModels.address.CustomerAddress;
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
    private int totalOrders;     //Total Number Of Orders

    private CustomerAddress customerAddress;

    private List<CustomerOrderItemDTO> customerOrderItems;
}
