package com.coder.springjwt.controllers.customerPanelControllers.reviewsController;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.publicService.reviewsService.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(CustomerUrlMappings.PRODUCT_REVIEWS_CONTROLLER)
public class ReviewsController {


    @Autowired
    private ReviewsService reviewsService;

    @GetMapping(CustomerUrlMappings.UNREVIEWED_DELIVERED_PRODUCT)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> unReviewDeliveredProduct(@PathVariable(required = false) long id) {
        return this.reviewsService.unReviewDeliveredProduct(id);
    }

    @PostMapping(CustomerUrlMappings.SUBMIT_PRODUCT_REVIEW)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> submitProductReview(
                                                  @RequestParam(value = "id" ,required = true) long id,
                                                  @RequestParam(value = "rating" ,required = true) String rating,
                                                  @RequestParam(value = "reviewText" ,required = true) String reviewText,
                                                  @RequestParam(value = "file") MultipartFile file) {

        return this.reviewsService.submitProductReview(id , rating , reviewText , file);

    }

    @GetMapping(CustomerUrlMappings.GET_USER_REVIEWS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getUserReviews(@RequestParam  Integer page ,
                                            @RequestParam  Integer size) {
        return this.reviewsService.getUserReviews( page ,size);
    }

    @GetMapping(CustomerUrlMappings.GET_EDIT_REVIEW)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getEditReview(@PathVariable(value = "reviewId" ,required = true) long reviewId) {
        return this.reviewsService.getEditReview(reviewId);
    }



}
