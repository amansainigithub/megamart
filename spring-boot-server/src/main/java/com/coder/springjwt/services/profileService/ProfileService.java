package com.coder.springjwt.services.profileService;

import com.coder.springjwt.payload.customerPayloads.freshUserPayload.UserProfileUpdatePayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProfileService {
    ResponseEntity<?> getProfile(long id);

    ResponseEntity<?> updateCustomerProfile(UserProfileUpdatePayload userProfileUpdatePayload);

    ResponseEntity<?> resendEmailLink(long id);
}
