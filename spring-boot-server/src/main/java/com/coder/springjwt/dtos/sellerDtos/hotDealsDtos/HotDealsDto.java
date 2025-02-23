package com.coder.springjwt.dtos.sellerDtos.hotDealsDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotDealsDto {

    private long id;

    private String name;

    private String offerName;

    private String categoryId;

    @NotBlank
    @NotNull
    private String engineId;

    private String fileUrl;

    private String dealColor;

    //Optional
    private String redirectUrl;

    private Boolean status = Boolean.FALSE;
}
