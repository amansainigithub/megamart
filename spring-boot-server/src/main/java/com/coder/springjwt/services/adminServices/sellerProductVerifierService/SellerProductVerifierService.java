package com.coder.springjwt.services.adminServices.sellerProductVerifierService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerProductVerifierService {

    ResponseEntity<?> getSellerProductVerifierList(String username,int page,int size);

    ResponseEntity<?> getSellerProductUnderReviewList(String username,int page,int size);

    ResponseEntity<?> getFormBuilderFlyingByAdmin(String categoryId);

    ResponseEntity<?> getSellerProductByIdAdmin(String productId);

    ResponseEntity<?> getSellerProductUnderReviewNoVariantList(String username, int page, int size);
}
