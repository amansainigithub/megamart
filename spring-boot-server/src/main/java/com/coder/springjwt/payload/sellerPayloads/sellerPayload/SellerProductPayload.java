package com.coder.springjwt.payload.sellerPayloads.sellerPayload;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SellerProductPayload {

    private  Long id;

    private String username;

    private String categoryId;

    private String categoryName;

    private String sellerStoreName;

    private List<ProductVariantPayload> productVariantPayloads;

}
