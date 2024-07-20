package com.coder.springjwt.services.customerServices.FreshUserService.imple;

import com.coder.springjwt.models.customerModels.FreshUser.FreshUser;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.FreshUserPayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.VerifyMobileOtpPayload;
import com.coder.springjwt.payload.response.MessageResponse;
import com.coder.springjwt.repository.customerRepo.freshUserRepo.FreshUserRepository;
import com.coder.springjwt.services.customerServices.FreshUserService.FreshUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FreshUserServiceImple implements FreshUserService {

    @Autowired
    private FreshUserRepository freshUserRepository;

    @Override
    public ResponseEntity<?> saveFreshUser(FreshUserPayload freshUserPayload) {

        FreshUser fuser = new FreshUser();
        fuser.setUsername(freshUserPayload.getUsername());

        //save OTP
        this.saveOTP(fuser);

        this.freshUserRepository.save(fuser);

        return ResponseEntity.ok(new MessageResponse("Fresh User Register Success"));
    }

    @Override
    public FreshUser findByUsername(String username) {
        FreshUser freshUser = this.freshUserRepository.findByUsername(username);
        return freshUser;
    }


    public FreshUser saveOTP(FreshUser freshUser)
    {
        String otp = "12345";
        freshUser.setMobileOtp(otp);
        return freshUser;
    }

    @Override
    public ResponseEntity<?> verifyMobileOtp(VerifyMobileOtpPayload verifyMobileOtpPayload) {
       FreshUser freshUser =  this.freshUserRepository.findByUsername
                (verifyMobileOtpPayload.getUsername());
       if(verifyMobileOtpPayload.getMobileOtp().equals(freshUser.getMobileOtp()))
       {
           freshUser.setIsVerified("Y");
           this.freshUserRepository.save(freshUser);
           return ResponseEntity.ok(freshUser);
       }else{
           return ResponseEntity.badRequest().build();
       }

    }
}
