package com.coder.springjwt.formBuilderTools.formVariableKeys;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProductRootBuilder {

    private long  id;

    private String  productName;
    private String  gst;
    private String  hsn;
    private String productCode;
    private List<String> productSizes;

    //Product table Data
    private List<ProductRows> tableRows;

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

    //Product Other Details
    private String numberOfItems;
    private String finishingType;
    private String brandField;
    private String description;

}
