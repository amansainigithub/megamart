package com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CustomerReturnOrderDto {

    private Long id;

    //RETURN REASON
    private String returnReason;

    //BANK DETAILS
    private String accountNumber;
    private String ifscCode;
    private String bankName;

    //DELIVERY DATE TIME PICKUP
    private String deliveryPickUpDateTime;

    //AMOUNT REFUND ID
    private String returnRefundStatus;
    private String returnRefundRequestDateTime;
    private String returnRefundProcessedDateTime;
    private String returnRefundId;

    private String deliveryStatus;
    private long orderItemId;

}
