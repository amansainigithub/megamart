package com.coder.springjwt.services.sellerServices.sellerTaxService;

import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerTaxPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerTaxService {
    public ResponseEntity<?> saveAndVerifyTaxDetails(SellerTaxPayload sellerTaxPayload);

}
