package com.coder.springjwt.dtos.customerPanelDtos;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChildCategoryDto {

    private long id;

    private String childCategoryName;

    private List<BabyCategoryDto> babyCategoryDtos;

}
