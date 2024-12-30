package com.coder.springjwt.payload.sellerPayloads.sellerProducts;

import lombok.Data;
import java.util.List;

@Data
public class SellerProductPayloads {


    private long id;

    private String productName;

    private String productStatus;

    private String creationDate;

    private String creationTime;

    private String skuId;

    private String fileName;

    private String productSize;

    private String productColor;

    private String actualPrice;

    private List<SellerProductVariants> sellerProductVariants;

}
