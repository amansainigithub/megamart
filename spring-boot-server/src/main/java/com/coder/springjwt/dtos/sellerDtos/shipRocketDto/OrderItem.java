package com.coder.springjwt.dtos.sellerDtos.shipRocketDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    public String name;
    public String sku;
    public int units;
    public double selling_price;
    public String discount;
    public String tax;
    public long hsn;
}
