package com.coder.springjwt.services.emailVerifierService.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.User;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.services.emailVerifierService.EmailVerifierService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class EmailVerifierServiceImple implements EmailVerifierService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> authTokenVerifier(String token) {
        log.info("<-- authTokenVerifier Flying -->");
       try {
           User user = this.userRepository.findByEmailVerificationToken(token)
                   .orElseThrow(() -> new DataNotFoundException(CustMessageResponse.DATA_NOT_FOUND));

           if (user.getEmailTokenExpiryTime().isBefore(LocalDateTime.now())) {
             return  ResponseGenerator.generateBadRequestResponse(CustMessageResponse.TOKEN_EXPIRED_RESEND_EMAIL_LINK
                                                            , CustMessageResponse.SOMETHING_WENT_WRONG);

//               return ResponseEntity.status(HttpStatus.FOUND)
//                       .header(HttpHeaders.LOCATION, "https://yourwebsite.com/error-page")
//                       .build();
           }

           user.setCustomerEmailVerify("Y");

           this.userRepository.save(user);
           return ResponseEntity.status(HttpStatus.FOUND)
                   .header(HttpHeaders.LOCATION, "http://localhost:4200/customer/profile")
                   .build();

       }
       catch (Exception e)
       {
           e.printStackTrace();
           return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
       }
    }
}
