package com.coder.springjwt.services.MobileOtpService;


import org.springframework.stereotype.Component;

@Component
public interface MobileOtpService {

    void sendSMS(String otp , String number , String messageContent);
}
