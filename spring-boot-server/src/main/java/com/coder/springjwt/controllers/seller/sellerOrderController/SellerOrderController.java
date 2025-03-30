package com.coder.springjwt.controllers.seller.sellerOrderController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.sellerOrdersService.SellerOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_ORDERS_CONTROLLER)
public class SellerOrderController {

    @Autowired
    private SellerOrdersService sellerOrdersService;


    @GetMapping(SellerUrlMappings.GET_PENDING_ORDER_BY_SELLER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getPendingOrderBySeller(@RequestParam Integer page , @RequestParam Integer size) {
        return this.sellerOrdersService.getPendingOrderBySeller(page , size);
    }

    @GetMapping(SellerUrlMappings.GET_SHIPPED_ORDER_BY_SELLER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getOrderBySeller(@RequestParam Integer page , @RequestParam Integer size) {
        return this.sellerOrdersService.getShippedOrderBySeller(page , size);
    }

    @GetMapping(SellerUrlMappings.GET_OUT_OF_DELIVERY_ORDER_BY_SELLER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getOutOfDeliveryOrderBySeller(@RequestParam Integer page , @RequestParam Integer size) {
        return this.sellerOrdersService.getOutOfDeliveryOrderBySellerService(page , size);
    }


    @GetMapping(SellerUrlMappings.GET_DELIVERED_ORDER_BY_SELLER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getDeliveredOrderBySeller(@RequestParam Integer page , @RequestParam Integer size) {
        return this.sellerOrdersService.getDeliveredOrderBySellerService(page , size);
    }
}
