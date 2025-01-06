package com.coder.springjwt.controllers.admin.adminSellerProductVerifyController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
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


    @GetMapping(AdminUrlMappings.GET_SELLER_PRODUCT_VERIFY_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSellerProductVerifyList() {
        return sellerProductVerifierService.getSellerProductVerifierList();
    }


    @GetMapping(AdminUrlMappings.GET_SELLER_PRODUCT_UNDER_REVIEW_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSellerProductUnderReviewList(@PathVariable String username ,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        return sellerProductVerifierService.getSellerProductUnderReviewList(username,page,size);
    }




}
