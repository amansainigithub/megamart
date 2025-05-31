package com.coder.springjwt.controllers.seller.sellerOrderCancelController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.refundDtos.RefundRequestDto;
import com.coder.springjwt.services.sellerServices.sellerCancelOrderService.SellerCancelOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_CANCEL_ORDER_CONTROLLER)
public class SellerOrderCancelController {

    @Autowired
    private SellerCancelOrderService sellerCancelOrderService;


    @GetMapping(SellerUrlMappings.SELLER_CANCEL_ORDERS_FETCH)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> sellerCancelOrdersFetch(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.sellerCancelOrderService.sellerCancelOrdersFetch(page,size);
    }

    @GetMapping(SellerUrlMappings.SELLER_CANCEL_ORDERS_FETCH_PAYMENT_COMPLETE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> sellerCancelOrdersFetchPaymentComplete(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.sellerCancelOrderService.sellerCancelOrdersFetchPaymentComplete(page,size);
    }

    @PostMapping(SellerUrlMappings.SELLER_ORDER_REFUND_REQUEST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> sellerOrderRefundRequest(@RequestBody RefundRequestDto refundRequestDto) {
        return this.sellerCancelOrderService.sellerOrderRefundRequest(refundRequestDto);
    }

    @PostMapping(SellerUrlMappings.SELLER_CANCEL_ORDERS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> sellerCancelOrders(@PathVariable long id) {
        return this.sellerCancelOrderService.sellerCancelOrders(id);
    }


}
