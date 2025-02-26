package com.coder.springjwt.response.sellerProductResponse;

import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
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
    private String variant;
    private String brandField;

    private String colorVariant;
    private String productPrice;
    private String productMrp;
    private String calculatedDiscount;

    private List<SellerProductVarientResponse> sellerProductVarientResponses;

    private List<ProductFilesResponse> productFilesResponses;

}
