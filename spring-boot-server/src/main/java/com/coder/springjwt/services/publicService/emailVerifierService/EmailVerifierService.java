package com.coder.springjwt.services.publicService.emailVerifierService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface EmailVerifierService {
    ResponseEntity<?> authTokenVerifier(String token);
}
