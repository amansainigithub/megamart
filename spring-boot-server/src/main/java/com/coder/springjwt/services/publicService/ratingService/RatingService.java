package com.coder.springjwt.services.publicService.ratingService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface RatingService {
    ResponseEntity<?> unratedDeliveredProduct();
}
