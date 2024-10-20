package com.coder.springjwt.payload.sellerPayloads.sellerPayload;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SellerCatalogPayload {

    private  String id;

    private String username;

    private String categoryId;

    private String categoryName;

    private String sellerStoreName;

    private String catalogTitle;

    private String catalogSubTitle;

    private String catalogFrontFile;

    private String catalogStatus;



}
