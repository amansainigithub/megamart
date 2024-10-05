package com.coder.springjwt.services.sellerServices.sellerBankService;

import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerBankPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerBankService {

    ResponseEntity<?> sellerBank(SellerBankPayload sellerBankPayload);

}
