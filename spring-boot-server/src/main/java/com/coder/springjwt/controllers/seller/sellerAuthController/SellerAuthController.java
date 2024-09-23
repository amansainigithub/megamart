package com.coder.springjwt.controllers.seller.sellerAuthController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.helpers.generateRandomNumbers.GenerateOTP;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.Role;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerMobile;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerOtpRequest;
import com.coder.springjwt.payload.request.SignupRequest;
import com.coder.springjwt.payload.response.JwtResponse;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerLoginPayload;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.security.services.UserDetailsImpl;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.sellerServices.sellerAuthService.SellerAuthService;
import com.coder.springjwt.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_BASE_URL)
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

//    @PostMapping(SellerUrlMappings.SELLER_SIGN_IN)
//    public ResponseEntity<?> sellerAuthenticateUser(@Validated @RequestBody SellerLoginPayload sellerLoginRequest) {
//
//        if(sellerLoginRequest.getUserrole().equals("ROLE_SELLER"))
//        {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(sellerLoginRequest.getUsername(), sellerLoginRequest.getPassword()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String jwt = jwtUtils.generateJwtToken(authentication);
//
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//            List<String> roles = userDetails.getAuthorities().stream()
//                    .map(item -> item.getAuthority())
//                    .collect(Collectors.toList());
//
//            for(String role : roles){
//                if(role.equals("ROLE_SELLER")){
//                    return ResponseEntity.ok(new JwtResponse(jwt,
//                            userDetails.getId(),
//                            userDetails.getUsername(),
//                            userDetails.getEmail(),
//                            roles));
//                }
//            }
//        } else{
//            throw new RuntimeException("Error: Unauthorized User");
//        }
//        return ResponseEntity.badRequest().body("Error: Unauthorized");
//
//    }

    @PostMapping(SellerUrlMappings.SELLER_MOBILE_CHECKER)
    public ResponseEntity<?> sellerMobileChecker(@Validated @RequestBody SellerMobile SellerMobile) {
        return sellerAuthService.sellerMobile(SellerMobile);
    }

    @PostMapping(SellerUrlMappings.VALIDATE_SELLER_OTP)
    public ResponseEntity<?> validateSellerOtp(@Validated @RequestBody SellerOtpRequest sellerOtpRequest) {
        return sellerAuthService.validateSellerOtp(sellerOtpRequest);
    }


    @PostMapping("sellerSignup")
    public ResponseEntity<?> sellerSignUp(@Validated @RequestBody SellerLoginPayload sellerLoginPayload) {

        System.out.println(sellerLoginPayload);
//        Set<String> strRoles = signUpRequest.getRole();
//
//        if(strRoles == null )
//        {
//            throw new RuntimeException("Role Not Found");
//        }
        if (userRepository.existsByUsername(sellerLoginPayload.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!", HttpStatus.BAD_REQUEST));
        }

        if (userRepository.existsByEmail(sellerLoginPayload.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!", HttpStatus.BAD_REQUEST));
        }

        // Create new user's account
        User user = new User(sellerLoginPayload.getEmail(),
                "ROLE_SELLER-" + GenerateOTP.generateOtpByAlpha(6) + "-" + sellerLoginPayload.getEmail(),
                encoder.encode(sellerLoginPayload.getPassword()));

        // Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        Role adminRole = roleRepository.findByName(ERole.ROLE_SELLER)
                .orElseThrow(() -> new RuntimeException("Error: Admin Role is not found."));
        roles.add(adminRole);

        user.setRoles(roles);

        //set Project Role
        user.setProjectRole(ERole.ROLE_SELLER.toString());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("ADMIN :: User registered successfully!",HttpStatus.CREATED));

    }








}
