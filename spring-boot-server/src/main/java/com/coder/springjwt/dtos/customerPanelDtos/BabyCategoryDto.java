package com.coder.springjwt.dtos.customerPanelDtos;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BabyCategoryDto {

    private long id;

    private String babyCategoryName;

    private List<BornCategoryDto> bornCategoryDtos;
}
