package com.coder.springjwt.services.customerServices.customerAuthService;

import com.coder.springjwt.payload.customerPayloads.customerPayload.CustomerLoginPayload;
import com.coder.springjwt.payload.customerPayloads.customerPayload.FreshSignUpPayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.FreshUserPayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.VerifyMobileOtpPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CustomerAuthService {

    public ResponseEntity<?> customerAuthenticateUser(CustomerLoginPayload customerLoginPayload);

    public ResponseEntity<?> CustomerSignUp(FreshUserPayload freshUserPayload);

    public ResponseEntity<?> verifyFreshUserMobileOtp(VerifyMobileOtpPayload verifyMobileOtpPayload);

    public  ResponseEntity<?> customerSignUpCompleted(FreshSignUpPayload freshSignUpPayload);
}
