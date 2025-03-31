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



}
