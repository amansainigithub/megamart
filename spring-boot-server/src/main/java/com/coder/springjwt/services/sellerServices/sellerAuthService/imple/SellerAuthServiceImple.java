package com.coder.springjwt.services.sellerServices.sellerAuthService.imple;

import com.coder.springjwt.helpers.OsLeaked.OsLeaked;
import com.coder.springjwt.helpers.generateRandomNumbers.GenerateOTP;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.Role;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerMobile;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerOtpRequest;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerLoginPayload;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.sellerMobileRepository.SellerMobileRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.sellerServices.sellerAuthService.SellerAuthService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class SellerAuthServiceImple implements SellerAuthService {

    @Autowired
     private SellerMobileRepository sellerMobileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService simpleEmailService;

    private static final long OTP_VALIDITY_DURATION = 1;

    @Override
    public ResponseEntity<?> sellerMobile(SellerMobile sellerMobile) {
        MessageResponse response = new MessageResponse();
        try {

            Optional<User>  verifySeller = this.userRepository.
                                        findBySellerMobileAndSellerRegisterComplete(sellerMobile.getMobile() , "Y");

            if(verifySeller.isPresent())
            {
                response.setMessage("ALREADY_VERIFIED");
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response,"SUCCESS");
            }

            Optional<SellerMobile> sellerDataNode =   this.sellerMobileRepository.findByMobile(sellerMobile.getMobile());
            //If Data is present in DB
            if(sellerDataNode.isPresent())
            {
                this.sellerMobileRepository.deleteById(sellerDataNode.get().getId());
                log.info("Deleted Success :: " + sellerDataNode.get().getId());
                ResponseEntity<?> responseEntity = this.saveSellerData(sellerMobile, response);
                return responseEntity;

            }else{
                ResponseEntity<?> responseEntity = this.saveSellerData(sellerMobile, response);
                return responseEntity;
            }
        }
        catch (Exception e)
        {
            response.setMessage("DATA NOT SAVED");
            response.setStatus(HttpStatus.BAD_REQUEST);
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(response);
        }

    }

    private ResponseEntity<?> saveSellerData(SellerMobile sellerMobile ,  MessageResponse response)
    {
        //Generate OTP FOR VALIDATE SELLER USER
        String otp =  GenerateOTP.generateOtp(6);
        log.info("Otp Generated Success  :: {}" + otp );
        sellerMobile.setOtp(otp);

        // Set expiration time to 5 minutes from now
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(OTP_VALIDITY_DURATION);
        sellerMobile.setExpiresAt(expiresAt);
        //save Seller Useer
        this.sellerMobileRepository.save(sellerMobile);

        log.info("Data saved Successfully");

        response.setMessage("OTP Sent Success");
        response.setStatus(HttpStatus.OK);
        return ResponseGenerator.generateSuccessResponse(response ,"DATA SAVED SUCCESS");
    }



    @Override
    public ResponseEntity<?> validateSellerOtp(SellerOtpRequest sellerOtpRequest) {
        MessageResponse response = new MessageResponse();

        Optional<SellerMobile> selleData = sellerMobileRepository.findByMobile(sellerOtpRequest.getMobile());

        if(selleData.isEmpty())
        {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("DATA_NOT_FOUND");
            return ResponseGenerator.generateBadRequestResponse(response);
        }else{
            SellerMobile sellerMobile = selleData.get();

                if(sellerMobile.getIsVerified().equals(Boolean.TRUE))
                {
                    this.sellerMobileRepository.deleteById(sellerMobile.getId());
                    log.info("Seller is Already Present => Seller Deleted Success :: {}" + sellerMobile.getId());
                    log.info("Seller Deleted Success :: {}" + sellerMobile.getId());
                    log.info("Flying to create new Seller ");
                }


            if(validateSellerOtp(sellerMobile , sellerOtpRequest))
            {
                //Set is Valid OTP TRUE...
                sellerMobile.setIsVerified(Boolean.TRUE);

                log.info("OTP SELLER MOBILE VERIFIED SUCCESS");
                response.setStatus(HttpStatus.OK);
                response.setMessage("OTP_VERIFIED_SUCCESS");
                return ResponseGenerator.generateSuccessResponse(response,"OTP VERIFIED SUCCESS");

            }
            else {
                System.out.println("OTP Expired OR Invalid");
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setMessage("OTP_EXPIRED_OR_INVALID");
                return ResponseGenerator.generateBadRequestResponse(response,"OTP VERIFIED FAILED");
            }

        }
    }

    public boolean validateSellerOtp(SellerMobile sellerMobile , SellerOtpRequest sellerOtpRequest)
    {
        if (sellerMobile != null) {

            // Check if OTP is expired
            System.out.println(" EXPIRED :: " + LocalDateTime.now().isBefore(sellerMobile.getExpiresAt()));

            if (LocalDateTime.now().isBefore(sellerMobile.getExpiresAt())) {
                // Compare OTPs
                if (sellerMobile.getOtp().equals(sellerOtpRequest.getOtp())) {
                    sellerMobile.setIsVerified(Boolean.TRUE);  // Invalidate OTP after successful use
                    sellerMobileRepository.save(sellerMobile);  // Save updated OTP entity
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public ResponseEntity<?> sellerSignUp(SellerLoginPayload sellerLoginPayload ,  HttpServletRequest request) {
        System.out.println(sellerLoginPayload);

        if (userRepository.existsByUsername(sellerLoginPayload.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email ID already taken!", HttpStatus.BAD_REQUEST));
        }

        if (userRepository.existsByEmail(sellerLoginPayload.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!", HttpStatus.BAD_REQUEST));
        }

        Optional<SellerMobile> sellerDataByMobileVerified = this.sellerMobileRepository.
                findByMobileAndIsVerified(sellerLoginPayload.getMobile(), Boolean.TRUE);

        if(sellerDataByMobileVerified.isPresent())
        {
            User user  = new User();

            //USERNAME
            user.setUsername(sellerLoginPayload.getEmail());

            //Set Email
            user.setEmail("ROLE_SELLER-" + GenerateOTP.generateOtpByAlpha(6) + "-" + sellerLoginPayload.getEmail());

            //set Password
            user.setPassword(encoder.encode(sellerLoginPayload.getPassword()));

            //Set Mobile
            user.setSellerMobile(sellerLoginPayload.getMobile());

            //set seller Email
            user.setSellerEmail(sellerLoginPayload.getEmail());

            //seller mobile Verified
            user.setSellerMobileVerify("Y");

            //seller Email Verified
            user.setSellerEmailVerify("N");

            //Set Registration Completed
            user.setSellerRegisterComplete("Y");

            //Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();

            Role adminRole = roleRepository.findByName(ERole.ROLE_SELLER)
                    .orElseThrow(() -> new RuntimeException("Error: Seller Role is not found."));
            roles.add(adminRole);

            user.setRoles(roles);

            //set Project Role
            user.setProjectRole(ERole.ROLE_SELLER.toString());

            //Save OS Data
            this.saveOsLeakedData(request , user);

            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("Seller Create Account Successfully !",HttpStatus.CREATED));

        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("USER_NOT_FOUND_HERE !! ", HttpStatus.BAD_REQUEST));

        }
    }


    public void saveOsLeakedData(HttpServletRequest request, User user)
    {
        log.info("Writing os data start");
        Map<String,String> node = OsLeaked.getOsData(request);
        user.setBrowserDetails(node.get("OsbrowserDetails"));
        user.setUserAgent(node.get("OsUserAgent"));
        user.setUserAgentVersion(node.get("osUser"));
        user.setOperatingSystem(node.get("operatingSystem"));
        user.setBrowserName(node.get("browserName"));
        log.info("Writing os data Ending");
    }

}
