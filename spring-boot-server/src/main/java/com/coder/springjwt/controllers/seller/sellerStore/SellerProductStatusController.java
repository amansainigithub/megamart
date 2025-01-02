package com.coder.springjwt.controllers.seller.sellerStore;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
