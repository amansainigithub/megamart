package com.coder.springjwt.dtos.customerPanelDtos.filterDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterDto {
    private List<String> brandKeys;
    private List<String> genders;
    private String price;
}
