package com.coder.springjwt.controllers.admin;

import com.coder.springjwt.constants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.payload.emailPayloads.EmailDetailsPayload;
import com.coder.springjwt.helpers.admin.RandomNumberGenerator;
import com.coder.springjwt.models.User;
import com.coder.springjwt.payload.request.LoginRequest;
import com.coder.springjwt.payload.request.Passkey;
import com.coder.springjwt.payload.response.JwtResponse;
import com.coder.springjwt.payload.response.MessageResponse;
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


    @PostMapping(AdminUrlMappings.ADMIN_SIGN_IN)
    public ResponseEntity<?> adminAuthenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        //Role Authentication only ADMIN can work
        roles.stream().forEach(e->{
           if(!e.equals("ROLE_ADMIN")){
               throw new RuntimeException("Cannot Access Role Missing!Error ");
           }
        });

        //Generate Pass Key
        String passKey = RandomNumberGenerator.generatePassKey(78);
        if(passKey == null || passKey.length() < 10){
            throw new RuntimeException("PassKey Error " + AdminAuthController.class.getName());
        }
        //send PassKey To E-MAIL
        EmailDetailsPayload emailDetailsPayload = new EmailDetailsPayload();
        emailDetailsPayload.setRecipient(userDetails.getEmail());
        emailDetailsPayload.setSubject("Pass-Key");
        emailDetailsPayload.setMsgBody("Hi Your Pass-Key : " + passKey);
        simpleEmailService.sendSimpleMail(emailDetailsPayload);


        System.out.println("PASS KEY :: " + passKey );
        User user =  this.userRepository.findByUsername(userDetails.getUsername()).get();
        user.setPassKey(passKey);
        userRepository.save(user);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


    @PostMapping(AdminUrlMappings.ADMIN_PASS_KEY)
    public ResponseEntity<?> passKey(@Validated @RequestBody Passkey passkey) {

        //check Passkey is Empty or Not
        if(passkey.getPassKey().equals(null)  || passkey.getPassKey().equals("")
           || passkey.getPassKey().equals("undefined") ){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("PassKey is Empty"));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(passkey.getUsername(), passkey.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        //Fetch Current User to DB
        User user = this.userRepository.findByUsername(userDetails.getUsername()).get();
        //Check Passkey is Authenticated or Not
        if(passkey.getPassKey().trim().equals(user.getPassKey())){
            System.out.println("Passkey matched success");
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error ! Invalid Passkey"));
        }

        //because of you have not entered the old passkey
        // everyTime that's why we have generated a new Passkey everyTime
        user.setPassKey("");
        this.userRepository.save(user);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));

    }
}
