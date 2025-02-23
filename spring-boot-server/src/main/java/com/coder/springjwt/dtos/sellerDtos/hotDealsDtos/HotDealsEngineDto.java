package com.coder.springjwt.dtos.sellerDtos.hotDealsDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotDealsEngineDto {

    private long id;

    private String dealName;

    private String description;

    private Boolean status = Boolean.FALSE;

}
