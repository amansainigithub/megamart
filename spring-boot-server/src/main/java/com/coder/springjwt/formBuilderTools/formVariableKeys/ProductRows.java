package com.coder.springjwt.formBuilderTools.formVariableKeys;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductRows {

    private long id;
    private String productLabel;
    private String productPrice;
    private String productMrp;
    private String productLength;
    private String skuId;

}
