package com.coder.springjwt.services.sellerServices.sellerAuthService;

import com.coder.springjwt.models.sellerModels.SellerMobile.SellerMobile;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerOtpRequest;
import com.coder.springjwt.payload.customerPayloads.customerPayload.CustomerLoginPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerAuthService {


    public ResponseEntity<?> sellerMobile(SellerMobile sellerMobile);

    public ResponseEntity<?> validateSellerOtp(SellerOtpRequest sellerOtpRequest);



}
