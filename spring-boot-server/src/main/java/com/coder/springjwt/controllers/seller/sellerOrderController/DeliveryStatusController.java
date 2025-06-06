package com.coder.springjwt.controllers.seller.sellerOrderController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusDto;
import com.coder.springjwt.services.sellerServices.deliveryStatusService.DeliveryStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.DELIVERY_STATUS_CONTROLLER)
public class DeliveryStatusController {

    @Autowired
    private DeliveryStatusService deliveryStatusService;

    @PostMapping(SellerUrlMappings.UPDATE_PENDING_DELIVERY_STATUS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updatePendingDeliveryStatus(@RequestBody DeliveryStatusDto deliveryStatusDto) {
        return this.deliveryStatusService.updatePendingDeliveryStatus(deliveryStatusDto);
    }


    @GetMapping(SellerUrlMappings.GET_DELIVERY_DETAILS_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getDeliveryDetailsById(@PathVariable Long id) {
        return this.deliveryStatusService.getDeliveryDetailsById(id);
    }

    @PostMapping(SellerUrlMappings.AWB_NUMBER_MAPPING)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> awbNumberMapping(@PathVariable Long id) {
        return this.deliveryStatusService.awbNumberMapping(id);
    }


    @GetMapping(SellerUrlMappings.DOWNLOAD_SHIPPING_LABEL)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> downloadShippingLabel(@PathVariable Long shipmentId) {
        return this.deliveryStatusService.downloadShippingLabel(shipmentId);
    }



}
