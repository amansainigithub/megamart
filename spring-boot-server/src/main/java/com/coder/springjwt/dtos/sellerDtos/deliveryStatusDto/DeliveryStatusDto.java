package com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryStatusDto {

    @NotNull(message = "Product Length cannot be null")
    @NotBlank(message = "Product Length cannot be empty")
    private String productLength;

    @NotNull(message = "Product Breadth cannot be null")
    @NotBlank(message = "Product Breadth cannot be empty")
    private String productBreadth;

    @NotNull(message = "Product Height cannot be null")
    @NotBlank(message = "Product Height cannot be empty")
    private String productHeight;

    @NotNull(message = "Product Weight cannot be null")
    @NotBlank(message = "Product Weight cannot be empty")
    private String productWeight;

    @NotNull(message = "Order Item cannot be null")
    @NotBlank(message = "Order Item cannot be empty")
    private String orderItemId;

}
