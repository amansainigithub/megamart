package com.coder.springjwt.controllers.admin.AdminProductStatusController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.payload.adminPayloads.ProductReviewPayload;
import com.coder.springjwt.services.adminServices.productReviewDecisionService.ProductReviewDecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(AdminUrlMappings.ADMIN_PRODUCT_REVIEW_DECISION_CONTROLLER)
@RestController
public class ProductReviewController{

    @Autowired
    private ProductReviewDecisionService productReviewDecisionService;

    @PostMapping(AdminUrlMappings.SAVE_PRODUCT_REVIEW_DECISION)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveProductReviewDecision(@Validated @RequestBody ProductReviewPayload productReviewPayload) {
        return this.productReviewDecisionService.saveProductReviewDecisionService(productReviewPayload);
    }



}
