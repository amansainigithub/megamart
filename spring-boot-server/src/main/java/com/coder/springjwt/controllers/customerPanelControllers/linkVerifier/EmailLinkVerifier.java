package com.coder.springjwt.controllers.customerPanelControllers.linkVerifier;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.emailVerifierService.EmailVerifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CustomerUrlMappings.EMAIL_LINK_VERIFIER)
public class EmailLinkVerifier {

    @Autowired
    private EmailVerifierService emailVerifierService;

    @GetMapping(CustomerUrlMappings.AUTH_TOKEN_VERIFIER)
    public ResponseEntity<?> authTokenVerifier(@RequestParam(required = true) String token) {
        return this.emailVerifierService.authTokenVerifier(token);
    }
}
