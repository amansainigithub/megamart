package com.coder.springjwt.dtos.sellerDtos.shipRocketDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestSRDto {

    public String order_id;
    public String order_date;
    public String pickup_location;
    public String comment;
    public String billing_customer_name;
    public String billing_last_name;
    public String billing_address;
    public String billing_address_2;
    public String billing_city;
    public int billing_pincode;
    public String billing_state;
    public String billing_country;
    public String billing_email;
    public long billing_phone;
    public boolean shipping_is_billing;
    public String shipping_customer_name;
    public String shipping_last_name;
    public String shipping_address;
    public String shipping_address_2;
    public String shipping_city;
    public String shipping_pincode;
    public String shipping_country;
    public String shipping_state;
    public String shipping_email;
    public String shipping_phone;
    public List<OrderItem> order_items;
    public String payment_method;
    public double shipping_charges;
    public double giftwrap_charges;
    public double transaction_charges;
    public double total_discount;
    public double sub_total;
    public double length;
    public double breadth;
    public double height;
    public double weight;
}
