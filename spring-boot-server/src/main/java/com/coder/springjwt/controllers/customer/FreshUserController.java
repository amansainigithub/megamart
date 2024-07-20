package com.coder.springjwt.controllers.customer;

import com.coder.springjwt.constants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.models.customerModels.FreshUser.FreshUser;
import com.coder.springjwt.payload.customerPayloads.customerPayload.CustomerLoginPayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.FreshUserPayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.VerifyMobileOtpPayload;
import com.coder.springjwt.payload.response.MessageResponse;
import com.coder.springjwt.services.customerServices.FreshUserService.FreshUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(CustomerUrlMappings.CUSTOMER_BASE_URL)
public class FreshUserController {


    @Autowired
    private FreshUserService freshUserService;

    @PostMapping(CustomerUrlMappings.SAVE_FRESH_USER)
    public ResponseEntity<?> saveFU(@Validated @RequestBody FreshUserPayload freshUserPayload) {
        ResponseEntity<?>  responseEntity= this.freshUserService.saveFreshUser(freshUserPayload);

        return ResponseEntity.ok(responseEntity);
    }

    @PostMapping(CustomerUrlMappings.GET_FRESH_USER)
    public ResponseEntity<?> getFreshUser(@Validated @RequestBody FreshUserPayload freshUserPayload) {
        FreshUser user = this.freshUserService.findByUsername(freshUserPayload.getUsername());

        HashMap<String,String> node = new HashMap<>();
        return ResponseEntity.ok(user);
    }

    @PostMapping(CustomerUrlMappings.VERIFY_FRESH_USER_MOBILE_OTP)
    public ResponseEntity<?> verifyFreshUserMobileOtp(@Validated @RequestBody VerifyMobileOtpPayload verifyMobileOtpPayload) {
        return this.freshUserService.verifyMobileOtp(verifyMobileOtpPayload);
    }


}