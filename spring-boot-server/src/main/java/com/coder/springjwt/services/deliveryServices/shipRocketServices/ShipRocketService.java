package com.coder.springjwt.services.deliveryServices.shipRocketServices;

import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusDto;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShipRocketService {


    ResponseEntity<String> createOrder(CustomerOrderItems customerOrderItems ,
                                  ProductVariants productVariant ,
                                  DeliveryStatusDto deliveryStatusDto);


    ResponseEntity<String> orderDetails(int orderId);

    ResponseEntity<String> cancelOrders(List<Integer> orderIds);

    ResponseEntity<String> getTrackingUrl(String awbCode);

    ResponseEntity<String> shippingLabelDownload(String shipmentId);

}
