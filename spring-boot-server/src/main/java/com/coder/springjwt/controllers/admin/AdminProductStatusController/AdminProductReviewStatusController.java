package com.coder.springjwt.controllers.admin.AdminProductStatusController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.productStatusDtos.ProductReviewStatusDto;
import com.coder.springjwt.services.adminServices.productStatusService.ProductStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(AdminUrlMappings.ADMIN_PRODUCT_STATUS_REVIEW_CONTROLLER)
@RestController
public class AdminProductReviewStatusController {

    @Autowired
    private ProductStatusService productStatusService;

    @PostMapping(AdminUrlMappings.SAVE_PRODUCT_REVIEW_STATUS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveProductReviewStatus(@Validated @RequestBody ProductReviewStatusDto productReviewStatusDto) {
        return this.productStatusService.saveProductReviewStatus(productReviewStatusDto);
    }


    @PostMapping(AdminUrlMappings.DELETE_PRODUCT_REVIEW_STATUS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProductReviewStatus(@PathVariable(required = true) long id) {
        return this.productStatusService.deleteProductReviewStatus(id);
    }


    @GetMapping(AdminUrlMappings.GET_PRODUCT_REVIEWS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProductReviews(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productStatusService.getProductReviews(page , size);
    }

    @GetMapping(AdminUrlMappings.GET_PRODUCT_REVIEW_STATUS_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProductReviews(@PathVariable(required = true) long id) {
        return this.productStatusService.getProductReviewStatusById(id);
    }

    @PostMapping(AdminUrlMappings.UPDATE_PRODUCT_REVIEW_STATUS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProductReviewStatus(@Validated @RequestBody ProductReviewStatusDto productReviewStatusDto) {
        return this.productStatusService.updateProductReviewStatus(productReviewStatusDto);
    }

    @GetMapping(AdminUrlMappings.GET_PRODUCT_REVIEWS_STATUS_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProductReviewsStatusList() {
        return this.productStatusService.getProductReviewsStatusList();
    }


}
