package com.coder.springjwt.controllers.customerPanelControllers.profileController;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.UserProfileUpdatePayload;
import com.coder.springjwt.services.publicService.profileService.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerUrlMappings.PROFILE_CONTROLLER)
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping(CustomerUrlMappings.GET_PROFILE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getProfile(@PathVariable(required = true) long id) {
        return this.profileService.getProfile(id);
    }

    @PostMapping(CustomerUrlMappings.UPDATE_CUSTOMER_PROFILE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateCustomerProfile(@Valid @RequestBody UserProfileUpdatePayload userProfileUpdatePayload) {
        return this.profileService.updateCustomerProfile(userProfileUpdatePayload);
    }

    @GetMapping(CustomerUrlMappings.RESEND_EMAIL_LINK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> resendEmailLink(@PathVariable(required = true) long id) {
        return this.profileService.resendEmailLink(id);
    }
}
