package com.coder.springjwt.controllers.admin.adminSellerProductVerifyController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.adminServices.sellerProductVerifierService.SellerProductVerifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequestMapping(AdminUrlMappings.ADMIN_SELLER_PRODUCT_VERIFY_CONTROLLER)
@RestController
public class AdminSellerProductVerifyController {

    @Autowired
    private SellerProductVerifierService sellerProductVerifierService;

    @GetMapping(AdminUrlMappings.FORM_BUILDER_FLYING_BY_ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> formBuilderFlyingByAdmin(@PathVariable String categoryId){
        return this.sellerProductVerifierService.getFormBuilderFlyingByAdmin(categoryId);
    }

    @GetMapping(AdminUrlMappings.GET_SELLER_PRODUCT_VERIFY_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSellerProductVerifyList(@PathVariable String username ,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return sellerProductVerifierService.getSellerProductVerifierList(username,page,size);
    }

    @GetMapping(AdminUrlMappings.GET_SELLER_PRODUCT_UNDER_REVIEW_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSellerProductUnderReviewList(@PathVariable String username ,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        return sellerProductVerifierService.getSellerProductUnderReviewList(username,page,size);
    }

    @GetMapping(AdminUrlMappings.GET_SELLER_PRODUCT_UNDER_REVIEW_NO_VARIANT_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSellerProductUnderReviewNoVariantList(@PathVariable String username ,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        return sellerProductVerifierService.getSellerProductUnderReviewNoVariantList(username,page,size);
    }

    @GetMapping(AdminUrlMappings.GET_SELLER_PRODUCT_BY_ID_ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSellerProductByIdAdmin(@PathVariable String productId) {
        return sellerProductVerifierService.getSellerProductByIdAdmin(productId);
    }


    @GetMapping(AdminUrlMappings.GET_SELLER_PRODUCT_APPROVED_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSellerProductApprovedList(@PathVariable String username ,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return sellerProductVerifierService.getSellerProductApprovedList(username,page,size);
    }


    @GetMapping(AdminUrlMappings.GET_SELLER_VARIANT_PRODUCT_APPROVED_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSellerVariantProductApprovedList(@PathVariable String username ,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        return sellerProductVerifierService.getSellerVariantProductApprovedList(username,page,size);
    }






}
