package com.coder.springjwt.dtos.customerPanelDtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BabyCategoryPopularDto {

    private long id;

    private String categoryName;

    private String categoryFile;
}
