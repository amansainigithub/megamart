package com.coder.springjwt.controllers.seller.sellerProductVerifyController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.sellerProductVerifierService.SellerProductVerifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequestMapping(SellerUrlMappings.SELLER_PRODUCT_VERIFY_CONTROLLER)
@RestController
public class SellerProductVerifyController {

    @Autowired
    private SellerProductVerifierService sellerProductVerifierService;

    @GetMapping(SellerUrlMappings.FORM_BUILDER_FLYING_BY_ADMIN)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> formBuilderFlyingByAdmin(@PathVariable String categoryId){
        return this.sellerProductVerifierService.getFormBuilderFlyingByAdmin(categoryId);
    }

    @GetMapping(SellerUrlMappings.GET_SELLER_PRODUCT_UNDER_REVIEW_NO_VARIANT_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getSellerProductUnderReviewNoVariantList(@PathVariable String username ,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        return sellerProductVerifierService.getSellerProductUnderReviewNoVariantList(username,page,size);
    }

    @GetMapping(SellerUrlMappings.GET_SELLER_PRODUCT_BY_ID_ADMIN)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getSellerProductByIdAdmin(@PathVariable String productId) {
        return sellerProductVerifierService.getSellerProductByIdAdmin(productId);
    }

}
