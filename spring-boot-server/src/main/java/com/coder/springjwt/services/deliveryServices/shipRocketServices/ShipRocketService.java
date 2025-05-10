package com.coder.springjwt.services.deliveryServices.shipRocketServices;

import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusDto;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ShipRocketService {


    ResponseEntity<String> createOrder(CustomerOrderItems customerOrderItems ,
                                  ProductVariants productVariant ,
                                  DeliveryStatusDto deliveryStatusDto);




}
