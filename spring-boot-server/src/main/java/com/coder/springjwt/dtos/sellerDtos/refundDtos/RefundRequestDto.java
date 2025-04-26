package com.coder.springjwt.dtos.sellerDtos.refundDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RefundRequestDto {

    private long id;
    private String refundAmount;
    private String userId;

}
