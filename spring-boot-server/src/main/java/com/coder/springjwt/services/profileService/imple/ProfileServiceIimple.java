package com.coder.springjwt.services.profileService.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.UserProfileUpdatePayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.UserprofilePayload;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.services.profileService.ProfileService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileServiceIimple implements ProfileService {

    @Autowired
    private UserRepository userRepository;
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
}
