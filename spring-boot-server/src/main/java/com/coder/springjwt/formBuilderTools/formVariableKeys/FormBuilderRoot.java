package com.coder.springjwt.formBuilderTools.formVariableKeys;

import com.coder.springjwt.formBuilderTools.FormBuilderModel.FormBuilderTool;
import com.coder.springjwt.formBuilderTools.FormBuilderModel.ProductDataBuilder;
import com.coder.springjwt.formBuilderTools.FormBuilderModel.SizeDataBuilder;
import com.coder.springjwt.formBuilderTools.FormBuilderModel.TableDataBuilder;
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

    private List<ProductDataBuilder> productDetailsBuilderList;

    private List<ProductDataBuilder> productDescAndOtherBuilderList;

    private List<SizeDataBuilder> sizeDataBuilderList;

    private List<TableDataBuilder> tableDataBuilderList;


//=======================================

    private List<FormBuilderTool> productIdentityList;

    private List<FormBuilderTool> productSizes;

    private List<FormBuilderTool> productVariants;

    private List<FormBuilderTool> productDetails;

    private List<FormBuilderTool> productOtherDetails;

}
