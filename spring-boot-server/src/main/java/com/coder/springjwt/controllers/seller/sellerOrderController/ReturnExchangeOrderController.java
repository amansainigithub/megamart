package com.coder.springjwt.controllers.seller.sellerOrderController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.returnOrderService.ReturnExchnageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.RETURN_EXCHANGE_ORDER_CONTROLLER)
public class ReturnExchangeOrderController {

    @Autowired
    private ReturnExchnageOrderService returnExchnageOrderService;

    @PostMapping(SellerUrlMappings.EXCHANGE_DELIVERY_STATUS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> exchangeDeliveryStatus(@PathVariable Long id,@PathVariable String exchangeDeliveryStatus) {
        return this.returnExchnageOrderService.exchangeDeliveryStatus(id,exchangeDeliveryStatus);
    }

    @PostMapping(SellerUrlMappings.EXCHANGE_PICKUP_DATE_TIME)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> exchangePickupDateTime(@PathVariable Long id,@PathVariable String pickupDateTime) {
        return this.returnExchnageOrderService.exchangePickupDateTime(id,pickupDateTime);
    }

}
