package com.coder.springjwt.controllers.seller.sellerOrderController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.sellerOrdersService.SellerOrderOneByOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_ORDERS_ONE_BY_ONE_CONTROLLER)
public class SellerOrderOneByOneController {

    @Autowired
    private SellerOrderOneByOneService sellerOrderOneByOneService;

    @GetMapping(SellerUrlMappings.GET_PENDING_ORDER_ONE_BY_ONE_BY_SELLER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getPendingOrderOneByOneBySeller(@RequestParam Integer page , @RequestParam Integer size) {
        return this.sellerOrderOneByOneService.getPendingOrderOneByOneBySeller(page , size);
    }

    @GetMapping(SellerUrlMappings.GET_SHIPPED_ORDER_ONE_BY_ONE_BY_SELLER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getShippedOrderOneByOneBySeller(@RequestParam Integer page , @RequestParam Integer size) {
        return this.sellerOrderOneByOneService.getShippedOrderOneByOneBySeller(page , size);
    }

    @GetMapping(SellerUrlMappings.GET_OUT_OF_DELIVERY_ORDER_ONE_BY_ONE_BY_SELLER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getOutOfDeliveryOrderOneByOneBySeller(@RequestParam Integer page , @RequestParam Integer size) {
        return this.sellerOrderOneByOneService.getOutOfDeliveryOrderOneByOneBySeller(page , size);
    }

    @GetMapping(SellerUrlMappings.GET_DELIVERED_ORDER_ONE_BY_ONE_BY_SELLER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getDeliveredOrderOneByOneBySeller(@RequestParam Integer page , @RequestParam Integer size) {
        return this.sellerOrderOneByOneService.getDeliveredOrderOneByOneBySeller(page , size);
    }
}
