package com.coder.springjwt.dtos.sellerDtos.homeSliderDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeSliderDto {

    private long id;

    private String title;

    private String slug;

    private String fileUrl;

    private String categoryId;

    private String categoryName;

    private Boolean status = Boolean.FALSE;
}
