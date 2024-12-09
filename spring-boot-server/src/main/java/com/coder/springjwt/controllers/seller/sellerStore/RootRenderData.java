package com.coder.springjwt.controllers.seller.sellerStore;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class RootRenderData {

    private String name;
    private String  address;
    private String  gst;
    private String  hsn;
    private String styleCode;
    private String netWight;

    private List<RowData> rowData;

}
