package com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryStatusUpdateDto {

    @NotNull(message = "Delivery status cannot be null")
    @NotBlank(message = "Delivery status cannot be empty")
//    @Pattern(regexp = "PENDING|SHIPPED|DELIVERED", message = "Invalid delivery status. Allowed values: PENDING, SHIPPED, DELIVERED")
    private String updateDeliveryStatus;

    @NotNull(message = "orderItemId  cannot be null")
    @NotBlank(message = "orderItemId cannot be empty")
    private String orderItemId;
}
