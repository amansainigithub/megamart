package com.coder.springjwt.controllers.seller.productStatusController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.payload.sellerPayloads.ProductReviewPayload;
import com.coder.springjwt.services.sellerServices.productReviewDecisionService.ProductReviewDecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(SellerUrlMappings.PRODUCT_REVIEW_DECISION_CONTROLLER)
@RestController
public class ProductReviewController{

    @Autowired
    private ProductReviewDecisionService productReviewDecisionService;

    @PostMapping(SellerUrlMappings.SAVE_PRODUCT_REVIEW_DECISION)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveProductReviewDecision(@Validated @RequestBody ProductReviewPayload productReviewPayload) {
        return this.productReviewDecisionService.saveProductReviewDecisionService(productReviewPayload);
    }



}
