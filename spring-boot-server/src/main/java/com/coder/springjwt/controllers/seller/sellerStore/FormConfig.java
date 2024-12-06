package com.coder.springjwt.controllers.seller.sellerStore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormConfig {
    private List<FormField> fields;

    // Getters and Setters
}
