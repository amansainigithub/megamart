package com.coder.springjwt.services.publicService.reviewsService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ReviewsService {
    public ResponseEntity<?> unReviewDeliveredProduct(Integer page, Integer size);

    ResponseEntity<?> submitProductReview(long id  ,String rating, String reviewText, MultipartFile file);


    ResponseEntity<?> getUserReviews( Integer page, Integer size);

    ResponseEntity<?> getEditReview(long reviewId);

    ResponseEntity<?> updateReviews(long id, String rating, String reviewText, MultipartFile file);

}
