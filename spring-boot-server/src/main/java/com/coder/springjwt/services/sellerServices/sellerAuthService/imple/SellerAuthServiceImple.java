package com.coder.springjwt.services.sellerServices.sellerAuthService.imple;

import com.coder.springjwt.helpers.generateRandomNumbers.GenerateOTP;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerMobile;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerOtpRequest;
import com.coder.springjwt.repository.sellerRepository.sellerMobileRepository.SellerMobileRepository;
import com.coder.springjwt.services.sellerServices.sellerAuthService.SellerAuthService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class SellerAuthServiceImple implements SellerAuthService {


    @Autowired
     private SellerMobileRepository sellerMobileRepository;

    private static final long OTP_VALIDITY_DURATION = 2;

    @Override
    public ResponseEntity<?> sellerMobile(SellerMobile sellerMobile) {
        MessageResponse response = new MessageResponse();
        try {
            //Generate OTP FOR VALIDATE SELLER USER
            String otp =  GenerateOTP.generateOtp(6);
            log.info("Otp Generated Success  :: {}" + otp );

            sellerMobile.setOtp(otp);

            // Set expiration time to 5 minutes from now
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(OTP_VALIDITY_DURATION);
            sellerMobile.setExpiresAt(expiresAt);

            response.setMessage("DATA SAVED SUCCESS");
            response.setStatus(HttpStatus.OK);

            //save Seller Useer
            SellerMobile sellerData = this.sellerMobileRepository.save(sellerMobile);

            return ResponseGenerator.generateSuccessResponse(response ,"DATA SAVED SUCCESS");

        }
        catch (Exception e)
        {
            response.setMessage("DATA NOT SAVED");
            response.setStatus(HttpStatus.BAD_REQUEST);
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(response);
        }

    }

    @Override
    public ResponseEntity<?> validateSellerOtp(SellerOtpRequest sellerOtpRequest) {
        MessageResponse response = new MessageResponse();
        Optional<SellerMobile> selleData = sellerMobileRepository.findByMobile(sellerOtpRequest.getMobile());

        if(selleData.isEmpty())
        {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Data Not Found");
            return ResponseGenerator.generateBadRequestResponse(response);
        }

        SellerMobile sellerMobile = selleData.get();

        if(CheckExpiryAndValidateSellerOtp(sellerMobile , sellerOtpRequest))
        {
            System.out.println("OTP MOBILE VERIFIED SUCCESS");
            response.setStatus(HttpStatus.OK);
            response.setMessage("OTP Verified Success");
            return ResponseGenerator.generateSuccessResponse(response,"OTP VERIFIED SUCCESS");

        }
        else {
            System.out.println("OTP Expired OR Invalid");
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("OTP Expired OR Invalid");
            return ResponseGenerator.generateBadRequestResponse(response,"OTP VERIFIED FAILED");

        }
    }


    public boolean CheckExpiryAndValidateSellerOtp(SellerMobile sellerMobile , SellerOtpRequest sellerOtpRequest)
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
}
