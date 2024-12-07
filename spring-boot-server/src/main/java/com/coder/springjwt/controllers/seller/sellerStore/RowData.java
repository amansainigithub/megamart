package com.coder.springjwt.controllers.seller.sellerStore;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RowData {
    private String price;
    private String length;
    private String inventory;
    private String category;
}
