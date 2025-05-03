package com.coder.springjwt.dtos.customerPanelDtos.invoiceDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {

    private String businessAddress;


    private String customerName;
    private String area;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private String country;
    private String mobileNumber;

    private String paymentMode;
    private String invoiceNumber;
    private String invoiceDate;
    private String orderDate;
    private String orderNumber;

    private String productName;
    private String hsn;
    private String grossAmount;
    private String quantity;
    private String productSize;
    private String tax;
    private double totalPrice;

    private double gstAmount;

}
