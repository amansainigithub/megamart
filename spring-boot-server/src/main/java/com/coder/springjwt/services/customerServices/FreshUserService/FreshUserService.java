package com.coder.springjwt.services.customerServices.FreshUserService;

import com.coder.springjwt.models.customerModels.FreshUser.FreshUser;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.FreshUserPayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.VerifyMobileOtpPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface FreshUserService {

    FreshUser findByUsername(String username);

    ResponseEntity<?> saveFreshUser(FreshUserPayload freshUserPayload);

    ResponseEntity<?> verifyMobileOtp(VerifyMobileOtpPayload verifyMobileOtpPayload);


}
