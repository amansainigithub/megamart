package com.coder.springjwt.formBuilderTools.formVariableKeys;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductRows {

    private long id;
    private String variantSize;
    private String price;
    private String mrp;
    private String skuId;
    private String productLength;
    private String productBreath;
    private String productHeight;

}
