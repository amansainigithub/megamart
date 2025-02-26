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
    private List<SellerProductVarientResponse> sellerProductVarientResponses;

    private List<ProductFilesResponse> productFilesResponses;

}
