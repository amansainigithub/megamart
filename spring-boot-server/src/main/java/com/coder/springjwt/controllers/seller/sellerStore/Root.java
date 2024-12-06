package com.coder.springjwt.controllers.seller.sellerStore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Root {
    @JsonProperty("product_data")
    private List<ProductData> productData;

    // Getters and Setters
}