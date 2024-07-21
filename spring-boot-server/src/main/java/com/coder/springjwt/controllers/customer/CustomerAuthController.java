package com.coder.springjwt.controllers.customer;

import com.coder.springjwt.constants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.exception.customerException.InvalidMobileNumberException;
import com.coder.springjwt.helpers.OTP.MobileOTP;
import com.coder.springjwt.helpers.ValidateMobileNumber;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.Role;
import com.coder.springjwt.models.User;
import com.coder.springjwt.payload.customerPayloads.customerPayload.CustomerLoginPayload;
import com.coder.springjwt.payload.customerPayloads.customerPayload.FreshSignUpPayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.FreshUserPayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.VerifyMobileOtpPayload;
import com.coder.springjwt.payload.response.JwtResponse;
import com.coder.springjwt.payload.response.MessageResponse;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.security.services.UserDetailsImpl;
import com.coder.springjwt.services.MobileOtpService.MobileOtpService;
import com.coder.springjwt.services.emailServices.simpleEmailService.SimpleEmailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(CustomerUrlMappings.CUSTOMER_BASE_URL)
public class CustomerAuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private MobileOtpService mobileOtpService;


    Logger logger  = LoggerFactory.getLogger(CustomerAuthController.class);

    @PostMapping(CustomerUrlMappings.CUSTOMER_SIGN_IN)
    public ResponseEntity<?> customerAuthenticateUser(@Validated @RequestBody CustomerLoginPayload customerLoginPayload) {

//        try {  Thread.sleep(2000);}
//        catch (Exception e){ }

        if(customerLoginPayload.getUserrole().equals("ROLE_CUSTOMER"))
        {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(customerLoginPayload.getUsername(), customerLoginPayload.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            for(String role : roles){
                if(role.equals("ROLE_CUSTOMER")){
                    return ResponseEntity.ok(new JwtResponse(jwt,
                            userDetails.getId(),
                            userDetails.getUsername(),
                            userDetails.getEmail(),
                            roles));
                }
            }
        } else{
            throw new RuntimeException("Error: Unauthorized User");
        }
        return ResponseEntity.badRequest().body("Error: Unauthorized");

    }


    @PostMapping(CustomerUrlMappings.CUSTOMER_SIGN_UP)
    public ResponseEntity<?> CustomerSignUp(@Valid @RequestBody FreshUserPayload freshUserPayload) {

        //Validate Mobile Number
        if(!ValidateMobileNumber.isValid(freshUserPayload.getUsername())) {
            throw new InvalidMobileNumberException("Invalid Mobile Number");
        }

        if (userRepository.existsByUsername(freshUserPayload.getUsername())) {
            User user = userRepository.findByUsername(freshUserPayload.getUsername()).get();

            if(user.getRegistrationCompleted().equals("Y") && user.getIsMobileVerify().equals("Y"))
            {
                return ResponseEntity
                        .ok()
                        .body(new MessageResponse("FLY_LOGIN_PAGE"));
            }else{
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }

        }else {
            // Create new user's account
            User user = new User(freshUserPayload.getUsername(),
                    freshUserPayload.getUsername() + "no@gmail.com",
                    encoder.encode("password"),
                    freshUserPayload.getUsername());

            user.setMobile(freshUserPayload.getUsername());
            //Set Mobile OTP Verified
            user.setIsMobileVerify("N");
            //Set Reg Completed
            user.setRegistrationCompleted("N");

            //Mobile OTP Generator [Mobile]
            String otp = MobileOTP.generateOtp(6);

            System.out.println("OTP :: " + otp);
            logger.info("OTP SUCCESSFULLY GENERATED :: " + otp);
            mobileOtpService.sendSMS(otp,freshUserPayload.getUsername());
            user.setMobileOtp(otp);

            //Set<String> strRoles = customerSignUpRequest.getRole();
            Set<Role> roles = new HashSet<>();
            Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not 5 found."));
            roles.add(customerRole);

            user.setRoles(roles);

            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Fresh User Created Success!!!"));
        }
    }

    @PostMapping(CustomerUrlMappings.VERIFY_FRESH_USER_MOBILE_OTP)
    public ResponseEntity<?> verifyFreshUserMobileOtp(@Validated @RequestBody VerifyMobileOtpPayload verifyMobileOtpPayload) {
        return this.verifyMobileOtp(verifyMobileOtpPayload);
    }



    public ResponseEntity<?> verifyMobileOtp(VerifyMobileOtpPayload verifyMobileOtpPayload) {
        User user =  this.userRepository.findByUsername
                (verifyMobileOtpPayload.getUsername()).get();
        if(verifyMobileOtpPayload.getMobileOtp().equals(user.getMobileOtp()))
        {
            user.setIsMobileVerify("Y");
            this.userRepository.save(user);
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping(CustomerUrlMappings.CUSTOMER_SIGN_UP_COMPLETED)
    public ResponseEntity<?> customerSignUpCompleted(@Valid @RequestBody FreshSignUpPayload freshSignUpPayload) {

        User user = userRepository.findByUsername(freshSignUpPayload.getUsername()).
                orElseThrow(()-> new RuntimeException("User Not Fount"));

        if ( (user.getRegistrationCompleted().equals("N") ||  user.getRegistrationCompleted().isEmpty()
                || user.getRegistrationCompleted() == null)
                && user.getIsMobileVerify().equals("Y")) {

            user.setRegistrationCompleted("Y");
            user.setPassword(encoder.encode(freshSignUpPayload.getPassword()));
            userRepository.save(user);
            System.out.println("Registration Completed Fully");

            return ResponseEntity.ok(new MessageResponse("Registration Completed Fully"));
        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("Something Went Wrong"));
        }
    }






}
