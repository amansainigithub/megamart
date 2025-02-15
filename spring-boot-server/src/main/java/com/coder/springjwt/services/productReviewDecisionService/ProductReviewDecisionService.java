package com.coder.springjwt.services.productReviewDecisionService;

import com.coder.springjwt.payload.sellerPayloads.ProductReviewPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductReviewDecisionService {

    ResponseEntity<?> saveProductReviewDecisionService(ProductReviewPayload productReviewPayload);

}
