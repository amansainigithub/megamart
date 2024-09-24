package com.coder.springjwt.services.sellerServices.sellerAuthService;

import com.coder.springjwt.models.sellerModels.SellerMobile.SellerMobile;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerOtpRequest;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerTaxPayload;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerLoginPayload;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerAuthService {


    public ResponseEntity<?> sellerMobile(SellerMobile sellerMobile);

    public ResponseEntity<?> validateSellerOtp(SellerOtpRequest sellerOtpRequest);

    public ResponseEntity<?> sellerSignUp(SellerLoginPayload sellerLoginPayload ,  HttpServletRequest request);

    public ResponseEntity<?> saveAndVerifyTaxDetails(SellerTaxPayload sellerTaxPayload);



}
