package com.coder.springjwt.controllers.seller.sellerStore;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProductRootData {

    private String  productName;
    private String  gst;
    private String  hsn;
    private String productCode;
    //Product table Data
    private List<ProductRows> productRows;
    //product Size Data
    private List<String> productSize;


    //Product Details
    private String styleName;
    private String sleeveType;
    private String fitType;
    private String gender;
    private String materialType;
    private String productColor;
    private String country;
    private String pattern;
    private String manufactureName;

    //Other Details
    private String description;
    private String numberOfItems;
    private String finishingType;
    private String brandField;









}
