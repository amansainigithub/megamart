package com.coder.springjwt.dtos.customerPanelDtos;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParentCategoryDto {

    private long id;

    private String parentCategoryName;

//    private List<ChildCategoryDto> childCategoryDtos;

    private List<BabyCategoryDto> babyCategoryDtos;


}
