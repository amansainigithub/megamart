package com.coder.springjwt.formBuilderTools.FormBuilderModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDataBuilder {

    private String id;
    private String identifier;
    private String name;
    private String type;
    private boolean required;
    private String description;
    private String min;
    private String max;
    private String minLength;
    private String maxLength;
    private List<String> values;
}
