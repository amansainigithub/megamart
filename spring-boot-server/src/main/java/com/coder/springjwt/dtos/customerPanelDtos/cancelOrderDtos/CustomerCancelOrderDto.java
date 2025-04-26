package com.coder.springjwt.dtos.customerPanelDtos.cancelOrderDtos;

import lombok.*;

@Data
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCancelOrderDto {

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

    private String refundStatus;

    private String refundProcessedDateTime;

    private String refundId;


}
