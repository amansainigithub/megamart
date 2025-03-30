package com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeliveryStatusDto {


    @NotNull(message = "Delivery status cannot be null")
    @NotBlank(message = "Delivery status cannot be empty")
//    @Pattern(regexp = "PENDING|SHIPPED|DELIVERED", message = "Invalid delivery status. Allowed values: PENDING, SHIPPED, DELIVERED")
    private String deliveryStatus;

    @NotNull(message = "deliveryDate cannot be null")
    @NotBlank(message = "deliveryDate cannot be empty")
    private String deliveryDate;

    @NotNull(message = "tackerId cannot be null")
    @NotBlank(message = "tackerId cannot be empty")
    private String tackerId;

    @NotNull(message = "orderItemId  cannot be null")
    @NotBlank(message = "orderItemId cannot be empty")
    private String orderItemId;
}
