package com.coder.springjwt.dtos.customerPanelDtos.returnExchangeDto;

import lombok.*;

@Data
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequestDto {

    private String exchangeReason;

    private String productId;

    private String selectedLabel;

    private long orderItemId;


}
