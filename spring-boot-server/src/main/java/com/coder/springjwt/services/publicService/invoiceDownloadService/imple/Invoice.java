package com.coder.springjwt.services.publicService.invoiceDownloadService.imple;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Invoice {

    private String id;
    private String date;
    private String customerName;
    private Double totalAmount;
}
