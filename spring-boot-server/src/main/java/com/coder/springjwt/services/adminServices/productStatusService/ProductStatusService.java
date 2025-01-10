package com.coder.springjwt.services.adminServices.productStatusService;

import com.coder.springjwt.dtos.adminDtos.productStatusDtos.ProductReviewStatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductStatusService {
    ResponseEntity<?> saveProductReviewStatus(ProductReviewStatusDto productReviewStatusDto);

    ResponseEntity<?> deleteProductReviewStatus(long id);

    ResponseEntity<?> getProductReviews(Integer page, Integer size);

    ResponseEntity<?> getProductReviewStatusById(long id);

    ResponseEntity<?> updateProductReviewStatus(ProductReviewStatusDto productReviewStatusDto);

    ResponseEntity<?> getProductReviewsStatusList();
}
