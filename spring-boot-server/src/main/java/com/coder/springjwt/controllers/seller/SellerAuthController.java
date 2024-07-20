package com.coder.springjwt.controllers.seller;

import com.coder.springjwt.constants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.payload.response.JwtResponse;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerLoginPayload;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.security.services.UserDetailsImpl;
import com.coder.springjwt.services.emailServices.simpleEmailService.SimpleEmailService;
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
    private SimpleEmailService simpleEmailService;

    @PostMapping(SellerUrlMappings.SELLER_SIGN_IN)
    public ResponseEntity<?> sellerAuthenticateUser(@Validated @RequestBody SellerLoginPayload sellerLoginRequest) {

        try {  Thread.sleep(2000);}
        catch (Exception e){ }

        if(sellerLoginRequest.getUserrole().equals("ROLE_SELLER"))
        {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(sellerLoginRequest.getUsername(), sellerLoginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            for(String role : roles){
                if(role.equals("ROLE_SELLER")){
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



}
