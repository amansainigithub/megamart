package com.coder.springjwt.controllers.seller.sellerProductController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_PRODUCT_STATUS_CONTROLLER)
public class SellerProductStatusController {

    @Autowired
    private SellerProductStatusService sellerProductStatusService;

    @GetMapping(SellerUrlMappings.GET_ALL_INCOMPLETE_PRODUCT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllIncompleteProduct() {
            return sellerProductStatusService.getAllIncompleteProduct();
    }


    @GetMapping(SellerUrlMappings.GET_PRODUCT_VARIANT_BY_VARIANT_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getProductVariantByVariantId(@PathVariable Long variantId) {
        return sellerProductStatusService.getProductVariantByVariantId(variantId);
    }

    @GetMapping(SellerUrlMappings.GET_PENDING_PRODUCT_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getPendingProductList(@PathVariable String username ,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return sellerProductStatusService.getPendingProductList(username,page,size);
    }


    @GetMapping(SellerUrlMappings.GET_APPROVED_PRODUCT_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getApprovedProductList(@PathVariable String username ,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return sellerProductStatusService.getApprovedProductList(username,page,size);
    }



}
