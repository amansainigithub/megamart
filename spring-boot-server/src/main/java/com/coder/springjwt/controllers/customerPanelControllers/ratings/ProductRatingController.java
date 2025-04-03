package com.coder.springjwt.controllers.customerPanelControllers.ratings;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.publicService.profileService.ProfileService;
import com.coder.springjwt.services.publicService.ratingService.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CustomerUrlMappings.PRODUCT_RATING_CONTROLLER)
public class ProductRatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping(CustomerUrlMappings.UNRATED_DELIVERED_PRODUCT)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> unratedDeliveredProduct() {
        return this.ratingService.unratedDeliveredProduct();
    }

}
