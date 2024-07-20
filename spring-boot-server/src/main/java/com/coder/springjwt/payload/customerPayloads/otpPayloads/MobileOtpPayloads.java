package com.coder.springjwt.payload.customerPayloads.otpPayloads;


import lombok.Data;

@Data
public class MobileOtpPayloads {

    private String email;

    private String username;

    private String mobileOtp;
}
