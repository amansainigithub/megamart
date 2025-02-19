package com.coder.springjwt.payload.sellerPayloads.sellerPayload;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductSizesPayload {


    private String productColor;

    private String productSize;

    private String inventory;

    private String price;

    private String mrp;
}
