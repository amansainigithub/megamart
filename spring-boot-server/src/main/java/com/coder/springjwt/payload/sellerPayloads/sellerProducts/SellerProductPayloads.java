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

    private String fileName;

    private String fileUrl;

    private String productColor;

    private List<SellerProductVariantsPayloads> sellerProductVariantPayloads;

}
