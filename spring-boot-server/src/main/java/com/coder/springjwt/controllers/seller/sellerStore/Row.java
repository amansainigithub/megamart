package com.coder.springjwt.controllers.seller.sellerStore;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Row {

    private String price;
    private String length;
    private int inventory;
    private String category;
}
