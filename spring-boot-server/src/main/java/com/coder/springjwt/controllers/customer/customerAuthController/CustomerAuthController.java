package com.coder.springjwt.controllers.customer.customerAuthController;

import com.coder.springjwt.constants.customerConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.payload.customerPayloads.customerPayload.CustForgotPasswordPayload;
import com.coder.springjwt.payload.customerPayloads.customerPayload.CustomerLoginPayload;
import com.coder.springjwt.payload.customerPayloads.customerPayload.FreshSignUpPayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.FreshUserPayload;
import com.coder.springjwt.payload.customerPayloads.freshUserPayload.VerifyMobileOtpPayload;
import com.coder.springjwt.services.customerServices.customerAuthService.CustomerAuthService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(CustomerUrlMappings.CUSTOMER_BASE_URL)
public class CustomerAuthController {

    Logger logger  = LoggerFactory.getLogger(CustomerAuthController.class);

    @Autowired
    private CustomerAuthService customerAuthService;

    @PostMapping(CustomerUrlMappings.CUSTOMER_SIGN_IN)
    public ResponseEntity<?> customerAuthenticateUser(@Validated @RequestBody CustomerLoginPayload customerLoginPayload) {
        try {
            Thread.sleep(3000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return customerAuthService.customerAuthenticateUser(customerLoginPayload);
    }

    @PostMapping(CustomerUrlMappings.CUSTOMER_SIGN_UP)
    public ResponseEntity<?> CustomerSignUp(@Valid @RequestBody FreshUserPayload freshUserPayload , HttpServletRequest request) {
        return customerAuthService.CustomerSignUp(freshUserPayload , request);
    }

    @PostMapping(CustomerUrlMappings.VERIFY_FRESH_USER_MOBILE_OTP)
    public ResponseEntity<?> verifyFreshUserMobileOtp(@Validated @RequestBody VerifyMobileOtpPayload verifyMobileOtpPayload) {
       return customerAuthService.verifyFreshUserMobileOtp(verifyMobileOtpPayload);
    }

    @PostMapping(CustomerUrlMappings.CUSTOMER_SIGN_UP_COMPLETED)
    public ResponseEntity<?> customerSignUpCompleted(@Valid @RequestBody FreshSignUpPayload freshSignUpPayload) {

        return this.customerAuthService.customerSignUpCompleted(freshSignUpPayload);
    }

    @PostMapping(CustomerUrlMappings.CUSTOMER_FORGOT_PASSWORD)
    public ResponseEntity<?> customerForgotPassword(@Valid @RequestBody CustForgotPasswordPayload custForgotPasswordPayload) {

        return this.customerAuthService.customerForgotPassword(custForgotPasswordPayload);
    }

    @PostMapping(CustomerUrlMappings.CUSTOMER_FORGOT_PASSWORD_FINAL)
    public ResponseEntity<?> customerForgotPasswordFinal(@Valid @RequestBody CustForgotPasswordPayload custForgotPasswordPayload) {
        return this.customerAuthService.customerForgotPasswordFinal(custForgotPasswordPayload);
    }





//    ENCRYPTION AND DECRYPTION USING

    private static final String SECRET_KEY = "1234567890008iu7yhygtfredfvgbhgg"; // Use the same key as in Angular
    private final Gson gson = new Gson();
    private final SecretKeySpec secretKeySpec = getKeySpec(SECRET_KEY);
    @PostMapping("/sswordFinal")
    public ResponseEntity<?> sswordFinal(@RequestBody Map<String, String> encryptedData) throws Exception {
        System.out.println("STARTING DATA 1111 " );
        String encryptedJson = encryptedData.get("data");
        System.out.println("encrypted Data :: " + encryptedJson);
        String decryptedJson = decrypt(encryptedJson, secretKeySpec);
        Map<String, Object> data = gson.fromJson(decryptedJson, Map.class);

        // Do something with the data
        System.out.println("Decrypted JSON data: " + data);

        // Return the decrypted data as a response (for demonstration purposes)
        return ResponseEntity.ok(data);
    }

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(256);
        return keyGen.generateKey();
    }

    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData);
    }


    private static final String ALGORITHM = "AES";
    public static SecretKeySpec getKeySpec(String key) {
        return new SecretKeySpec(key.getBytes(), ALGORITHM);
    }





}
