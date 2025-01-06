package com.coder.springjwt.services.adminServices.sellerProductVerifierService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerProductVerifierService {
    ResponseEntity<?> getSellerProductVerifierList();

    ResponseEntity<?> getSellerProductUnderReviewList(String username,int page,int size);
}
