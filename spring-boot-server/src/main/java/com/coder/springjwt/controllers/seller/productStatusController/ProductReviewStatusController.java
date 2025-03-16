package com.coder.springjwt.controllers.seller.productStatusController;


import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.productStatusDtos.ProductReviewStatusDto;
import com.coder.springjwt.services.sellerServices.productStatusService.ProductStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(SellerUrlMappings.SELLER_PRODUCT_VERIFY_CONTROLLER)
@RestController
public class ProductReviewStatusController {

    @Autowired
    private ProductStatusService productStatusService;

    @PostMapping(SellerUrlMappings.SAVE_PRODUCT_REVIEW_STATUS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveProductReviewStatus(@Validated @RequestBody ProductReviewStatusDto productReviewStatusDto) {
        return this.productStatusService.saveProductReviewStatus(productReviewStatusDto);
    }


    @PostMapping(SellerUrlMappings.DELETE_PRODUCT_REVIEW_STATUS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteProductReviewStatus(@PathVariable(required = true) long id) {
        return this.productStatusService.deleteProductReviewStatus(id);
    }


    @GetMapping(SellerUrlMappings.GET_PRODUCT_REVIEWS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getProductReviews(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productStatusService.getProductReviews(page , size);
    }

    @GetMapping(SellerUrlMappings.GET_PRODUCT_REVIEW_STATUS_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getProductReviews(@PathVariable(required = true) long id) {
        return this.productStatusService.getProductReviewStatusById(id);
    }

    @PostMapping(SellerUrlMappings.UPDATE_PRODUCT_REVIEW_STATUS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateProductReviewStatus(@Validated @RequestBody ProductReviewStatusDto productReviewStatusDto) {
        return this.productStatusService.updateProductReviewStatus(productReviewStatusDto);
    }

    @GetMapping(SellerUrlMappings.GET_PRODUCT_REVIEWS_STATUS_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getProductReviewsStatusList() {
        return this.productStatusService.getProductReviewsStatusList();
    }


}
