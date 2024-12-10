package com.coder.springjwt.controllers.seller.sellerStore;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductRows {
    private String variantSize;
    private String price;
    private String mrp;
    private String skuId;
    private String productLength;
    private String productBreath;
    private String productHeight;

}
