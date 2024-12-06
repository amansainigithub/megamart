package com.coder.springjwt.controllers.seller.sellerStore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerDetails {

    private Long id;

    private String name;
    private String address;

    private String size; // New field for the dropdown value

    private List<String> colorVariant;

    private String password;
}
