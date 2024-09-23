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

            Optional<SellerMobile> sellerDataNode =   this.sellerMobileRepository.findByMobile(sellerMobile.getMobile());


            //If Data is Empty
            if(sellerDataNode.isEmpty() )
            {
                ResponseEntity<?> responseEntity = this.saveSellerData(sellerMobile, response);
                return responseEntity;

            }else if (!sellerDataNode.isEmpty() && sellerDataNode.get().getIsVerified() == Boolean.FALSE){
                //Deleted Data
                this.sellerMobileRepository.deleteById(sellerDataNode.get().getId());
                log.info("Data Deleted Success :: {} " + sellerDataNode.get().getId());

                // save New Seller Entry
                ResponseEntity<?> responseEntity = this.saveSellerData(sellerMobile, response);
                return responseEntity;

            }else if(!sellerDataNode.isEmpty() && sellerDataNode.get().getIsVerified() == Boolean.TRUE){
                response.setMessage("ALREADY_VERIFIED");
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response,"SUCCESS");
            }else{

                return ResponseGenerator.generateBadRequestResponse(response,"SOMETHING WENT WRONG");
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


        response.setMessage("OTP Sent Success");
        response.setStatus(HttpStatus.OK);

        //save Seller Useer
        this.sellerMobileRepository.save(sellerMobile);

        log.info("Data saved Successfully");

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
                response.setStatus(HttpStatus.OK);
                response.setMessage("YOU_ARE_ALREADY_AUTHENTICATED");
                return ResponseGenerator.generateSuccessResponse(response,"USER_ALREADY_VERIFIED");
            }

            if(CheckExpiryAndValidateSellerOtp(sellerMobile , sellerOtpRequest))
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
