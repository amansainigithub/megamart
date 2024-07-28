package com.coder.springjwt.services.adminServices.adminAuthService;

import com.coder.springjwt.payload.request.LoginRequest;
import com.coder.springjwt.payload.request.Passkey;
import com.coder.springjwt.payload.request.SignupRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public interface AdminAuthService {

    public ResponseEntity<?> adminAuthenticateUser(LoginRequest loginRequest);

    public ResponseEntity<?> passKey( Passkey passkey);

    public ResponseEntity<?> adminSignUp(SignupRequest signUpRequest);


}
