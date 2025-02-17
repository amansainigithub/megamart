package com.coder.springjwt.services.sellerServices.sellerProductVerifierService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerProductVerifierService {



    ResponseEntity<?> getFormBuilderFlyingByAdmin(String categoryId);

    ResponseEntity<?> getSellerProductByIdAdmin(String productId);

    ResponseEntity<?> getSellerProductUnderReviewNoVariantList(String username, int page, int size);
}
