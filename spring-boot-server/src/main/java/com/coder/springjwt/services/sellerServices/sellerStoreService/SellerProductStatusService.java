package com.coder.springjwt.services.sellerServices.sellerStoreService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerProductStatusService {

    ResponseEntity<?> getApprovedProductList(String username, int page, int size);
}
