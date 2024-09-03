package com.coder.springjwt.controllers.admin.adminAuthController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.payload.request.LoginRequest;
import com.coder.springjwt.payload.request.Passkey;
import com.coder.springjwt.payload.request.SignupRequest;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.services.MobileOtpService.MobileOtpService;
import com.coder.springjwt.services.MobileOtpService.imple.OtpServiceImple;
import com.coder.springjwt.services.adminServices.adminAuthService.AdminAuthService;
import com.coder.springjwt.services.emailServices.simpleEmailService.SimpleEmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping(AdminUrlMappings.AUTH_BASE_URL)
public class AdminAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private  JwtUtils jwtUtils;

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private AdminAuthService adminAuthService;


    @PostMapping(AdminUrlMappings.ADMIN_SIGN_IN)
    public ResponseEntity<?> adminAuthenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
      return adminAuthService.adminAuthenticateUser(loginRequest);
    }

    @PostMapping(AdminUrlMappings.ADMIN_PASS_KEY)
    public ResponseEntity<?> passKey(@Validated @RequestBody Passkey passkey) {
        return adminAuthService.passKey(passkey);
    }

    @PostMapping(AdminUrlMappings.ADMIN_SIGN_UP)
    public ResponseEntity<?> adminSignUp(@Valid @RequestBody SignupRequest signUpRequest) {
        return adminAuthService.adminSignUp(signUpRequest);
    }

//@Autowired
//OtpServiceImple otpServiceImple;
//    @PostMapping("sendsms")
//    public ResponseEntity<?> sendsms() {
//        try {
//             otpServiceImple.sendSMS("9818644140","New Number Send to OTP" ,"SELLER");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//


}
