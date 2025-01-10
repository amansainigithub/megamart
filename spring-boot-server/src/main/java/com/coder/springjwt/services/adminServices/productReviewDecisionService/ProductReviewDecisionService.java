package com.coder.springjwt.services.adminServices.productReviewDecisionService;

import com.coder.springjwt.payload.adminPayloads.ProductReviewPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductReviewDecisionService {

    ResponseEntity<?> saveProductReviewDecisionService(ProductReviewPayload productReviewPayload);

}
