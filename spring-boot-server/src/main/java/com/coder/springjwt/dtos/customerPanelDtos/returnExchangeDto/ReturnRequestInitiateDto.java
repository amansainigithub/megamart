package com.coder.springjwt.dtos.customerPanelDtos.returnExchangeDto;

import lombok.*;

@Data
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReturnRequestInitiateDto {

    private Long id;
    private String returnReason;
    private String accountNumber;
    private String ifscCode;
    private String bankName;

}


