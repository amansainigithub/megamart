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
public class ProductDataBuilder {

    private String id;
    private String identifier;
    private String name;
    private String type;
    private boolean required;
    private String description;
    private String min;
    private String max;
    private List<String> values;
}
