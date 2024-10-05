package com.coder.springjwt.controllers.seller.sellerAuthController;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerOtpRequest;
import com.coder.springjwt.payload.request.LoginRequest;
import com.coder.springjwt.payload.response.JwtResponse;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.*;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.security.services.UserDetailsImpl;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.sellerServices.sellerAuthService.SellerAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_AUTH_CONTROLLER)
public class SellerAuthController {

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
    private EmailService simpleEmailService;

    @Autowired
    SellerAuthService sellerAuthService;

    @PostMapping(SellerUrlMappings.SELLER_SIGN_IN)
    public ResponseEntity<?> sellerAuthenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

            Authentication authentication = authenticationManager.authenticate(
                                            new UsernamePasswordAuthenticationToken(
                                                    loginRequest.getUsername()+ SellerMessageResponse.SLR,
                                                    loginRequest.getPassword()));

        Optional<User> userData  = this.userRepository.findBySellerMobileAndSellerRegisterComplete
                                     ("9818644140", "Y");

        if(userData.isPresent())
        {
            SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());

                for(String role : roles){
                    if(role.equals(ERole.ROLE_SELLER.toString())){

                        return ResponseEntity.ok(new JwtResponse(jwt,
                                userDetails.getId(),
                                userDetails.getUsername(),
                                userDetails.getEmail(),
                                roles));

                    }
                }

        }
        return ResponseEntity.badRequest().body("Error: Unauthorized");
    }

    @PostMapping(SellerUrlMappings.SELLER_SEND_OTP)
    public ResponseEntity<?> sellerSendOtp(@Validated @RequestBody SellerMobilePayload sellerMobilePayload) {
        return sellerAuthService.sellerMobile(sellerMobilePayload);
    }

    @PostMapping(SellerUrlMappings.VALIDATE_SELLER_OTP)
    public ResponseEntity<?> validateSellerOtp(@Validated @RequestBody SellerOtpRequest sellerOtpRequest) {
        return sellerAuthService.validateSellerOtp(sellerOtpRequest);
    }

    @PostMapping(SellerUrlMappings.SELLER_SIGN_UP)
    public ResponseEntity<?> sellerSignUp(@Validated @RequestBody SellerLoginPayload sellerLoginPayload , HttpServletRequest request) {
        return sellerAuthService.sellerSignUp(sellerLoginPayload , request);
    }






}
