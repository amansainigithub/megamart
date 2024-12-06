package com.coder.springjwt.controllers.seller.sellerStore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormField {

    private String identifier;
    private String name;
    private String type;
    private boolean mandatory;
    private String description;
    private List<String> values; // List of possible values for dropdowns
    // Getters and Setters
}
