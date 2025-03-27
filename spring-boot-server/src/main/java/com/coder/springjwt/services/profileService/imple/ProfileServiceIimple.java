package com.coder.springjwt.services.profileService.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.customerEmailConstants.CustomerEmailContent;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.UserProfileUpdatePayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.UserprofilePayload;
import com.coder.springjwt.payload.emailPayloads.EmailHtmlPayload;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.profileService.ProfileService;
import com.coder.springjwt.services.publicService.customerAuthService.imple.CustomerAuthServiceImple;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ProfileServiceIimple implements ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerAuthServiceImple customerAuthServiceImple;
    @Autowired
    private EmailService emailService;

    @Override
    public ResponseEntity<?> getProfile(long id) {
        try {
            String currentUser = UserHelper.getOnlyCurrentUser();

            User user = this.userRepository.findByUsername(currentUser)
                                            .orElseThrow(()-> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));
            if(user.getId() == id ){

                UserprofilePayload userprofilePayload = new UserprofilePayload();
                userprofilePayload.setFirstName(user.getFirstName());
                userprofilePayload.setLastName(user.getLastName());
                userprofilePayload.setEmail(user.getCustomerEmail());
                userprofilePayload.setMobile(user.getMobile());
                userprofilePayload.setEmailVerify(user.getCustomerEmailVerify());
                userprofilePayload.setCustomerGender(user.getCustomerGender());

                return ResponseGenerator.generateSuccessResponse(userprofilePayload , CustMessageResponse.SOMETHING_WENT_WRONG);
            }else{
                return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> updateCustomerProfile(UserProfileUpdatePayload userProfileUpdatePayload) {
        try {
            String currentUser = UserHelper.getOnlyCurrentUser();

            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(()-> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            user.setFirstName(userProfileUpdatePayload.getFirstName());
            user.setLastName(userProfileUpdatePayload.getLastName());
            user.setCustomerEmail(userProfileUpdatePayload.getEmail());
            user.setCustomerGender(userProfileUpdatePayload.getCustomerGender());
            user.setCustomerEmailVerify("N");
            this.userRepository.save(user);
            return ResponseGenerator.generateSuccessResponse(CustMessageResponse.SUCCESS , CustMessageResponse.DATA_SAVED_SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public synchronized ResponseEntity<?> resendEmailLink(long id) {
       try {
           String currentUser = UserHelper.getOnlyCurrentUser();

           User user = this.userRepository.findByUsername(currentUser)
                   .orElseThrow(()-> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

           //Send
            this.sendResendEmailVerifyLink(user);
            user.setCustomerEmailVerify("N");


            this.userRepository.save(user);

            System.out.println("Email Sent Success | and Data Updated");
           return ResponseGenerator.generateSuccessResponse(CustMessageResponse.SUCCESS , CustMessageResponse.DATA_SAVED_SUCCESS);
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
       }
    }

    public void sendResendEmailVerifyLink(User user){
        try {
            //Generate Email Auth Token
            String emailAuthToken = emailService.generateVerificationToken();

            user.setEmailVerificationToken(emailAuthToken);
            user.setEmailTokenExpiryTime(LocalDateTime.now().plusMinutes(30));

            //Send Mail Registration Complete Successfully
            EmailHtmlPayload emailHtmlPayload = new EmailHtmlPayload();
            emailHtmlPayload.setHtmlContent(CustomerEmailContent.sendResendEmailLink(user.getFirstName() , emailAuthToken));
            emailHtmlPayload.setRole("ROLE_CUSTOMER");
            emailHtmlPayload.setRecipient(user.getCustomerEmail());
            emailHtmlPayload.setSubject("Resend Email Link PLease Verify");

            emailService.sendHtmlMail(emailHtmlPayload);
            System.out.println("Email Sent Success");
        }
        catch (Exception e)
        {
            System.out.println("Error in Registration sent Success Mail");
        }
    }


}
