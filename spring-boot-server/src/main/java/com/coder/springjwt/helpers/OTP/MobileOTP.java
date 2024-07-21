package com.coder.springjwt.helpers.OTP;

public class MobileOTP {

    public static String generateOtp(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                +"0123456789"
                +"abcdefghijklmnopqrstuvxyz"; ;

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }
}
