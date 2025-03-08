package com.coder.springjwt.response.sellerProductResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerProductResponse {

    private long id;
    private String  productName;
    private String productColor;
    private String productStatus;
    private String bornCategoryId;
    private String bornCategoryName;
    private String variant;
    private String brandField;

    private String colorVariant;
    private String productPrice;
    private String productMrp;
    private String calculatedDiscount;
    private String description;
    private String netQuantity;

    private String productFirstSize;

    private List<ProductDetailsResponse> productDetailsResponses;

    private List<SellerProductVarientResponse> sellerProductVarientResponses;

    private List<ProductFilesResponse> productFilesResponses;

}
