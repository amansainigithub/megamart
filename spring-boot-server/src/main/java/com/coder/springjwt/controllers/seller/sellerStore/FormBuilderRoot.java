package com.coder.springjwt.controllers.seller.sellerStore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FormBuilderRoot {

    private List<ProductDataBuilder> productDataBuilderList;

    private List<VariationsDataBuilder> variationsDataBuilderList;

    private List<SizeDataBuilder> sizeDataBuilderList;

    private List<TableDataBuilder> tableDataBuilderList;


}
