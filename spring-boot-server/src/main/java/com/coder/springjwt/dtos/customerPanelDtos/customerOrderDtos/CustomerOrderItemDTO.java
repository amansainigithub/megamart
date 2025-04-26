package com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CustomerOrderItemDTO {

    private Long id;

    private String productId;

    private String productName;

    private String productPrice;

    private String productBrand;

    private String productSize;

    private String quantity;

    private String fileUrl;

    private String deliveryStatus;

    private String orderTrackingId;

    private String productDiscount;

    private String productMrp;

    private String paymentMode;

    private String razorpayOrderId;

    private String orderDateTime;

    private String totalPrice;

    private String customOrderNumber;



    //Address Data

    private String customerName;

    private String addressId;

    private String country;

    private String mobileNumber;

    private String area;

    private String postalCode;

    private String addressLine1;

    private String addressLine2;

    private boolean defaultAddress;

    private String deliveryDateTime;

    private String courierName;


    //Razorpay Fees And status
    private String razorpayFees;
    private String razorpayGst;
    private String razorpayTotalFees;
    private String razorpayFinalAmt;

    private String refundStatus;
    private String refundRequestDateTime;
    private String refundProcessedDateTime;
    private String refundId;


}
