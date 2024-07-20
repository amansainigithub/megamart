package com.coder.springjwt.controllers.customer;

import com.coder.springjwt.constants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.Role;
import com.coder.springjwt.models.User;
import com.coder.springjwt.payload.customerPayloads.customerPayload.CustomerLoginPayload;
import com.coder.springjwt.payload.customerPayloads.customerPayload.CustomerSignUpPayload;
import com.coder.springjwt.payload.response.JwtResponse;
import com.coder.springjwt.payload.response.MessageResponse;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.security.services.UserDetailsImpl;
import com.coder.springjwt.services.emailServices.simpleEmailService.SimpleEmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<?> CustomerSignUp(@Valid @RequestBody CustomerSignUpPayload customerSignUpRequest) {
        if (userRepository.existsByUsername(customerSignUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(customerSignUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(customerSignUpRequest.getMobile(),
                customerSignUpRequest.getMobile()+"@gmail.com",
                encoder.encode(customerSignUpRequest.getPassword()),
                customerSignUpRequest.getUsername());

        //Set<String> strRoles = customerSignUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not 5 found."));
        roles.add(customerRole);

        user.setRoles(roles);

        user.setFirstName(customerSignUpRequest.getFirstName());
        user.setLastName(customerSignUpRequest.getLastName());

        user.setMobileOtp("12345");
        userRepository.save(user);



        return ResponseEntity.ok(new MessageResponse("Customer User registered successfully!"));
    }


}
