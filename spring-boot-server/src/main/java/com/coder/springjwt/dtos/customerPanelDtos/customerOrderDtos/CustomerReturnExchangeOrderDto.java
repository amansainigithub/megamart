package com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CustomerReturnExchangeOrderDto {

    private Long id;

    //RETURN REASON
    private String returnReason;

    //BANK DETAILS
    private String accountNumber;
    private String ifscCode;
    private String bankName;

    //DELIVERY DATE TIME PICKUP
    private String returnDeliveryPickUpDateTime;
    private String returnRefundStatus;
    private String returnRefundRequestDateTime;
    private String returnRefundProcessedDateTime;
    private String returnRefundId;
    private String returnDeliveryStatus;



    //Exchange Items Properties
    private String exchangeReason;
    private String exchangeProductSize;
    private String exchangeRequestDateTime;
    private String exchangeProcessedDateTime;
    private String exchangeDeliveryPickUpDateTime;
    private String exchangeDeliveryStatus;


    private String productId;
    private long orderItemId;

}
