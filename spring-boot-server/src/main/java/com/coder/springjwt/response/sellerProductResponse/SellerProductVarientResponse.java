package com.coder.springjwt.response.sellerProductResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerProductVarientResponse {

    private long id;

    private String colorVariant;
    private String productPrice;
    private String productMrp;
    private String calculatedDiscount;
    private String productLabel;
    private String productLength;

    private String productInventory;
    private String waistSize;
    private String shoulderWidth;
    private String chestBustSize;
}
