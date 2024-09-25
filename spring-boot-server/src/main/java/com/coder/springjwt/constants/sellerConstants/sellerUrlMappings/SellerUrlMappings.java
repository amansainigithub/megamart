package com.coder.springjwt.constants.sellerConstants.sellerUrlMappings;

public class SellerUrlMappings {

    //ADMIN MAPPING START
    //############## APP CONTEXT #####################
    public static final String APPLICATION_CONTEXT_PATH = "/shopping";

    //############## NOT-AUTH BASE URL #####################
    public static final String SELLER_BASE_URL = APPLICATION_CONTEXT_PATH + "/api/seller/v1";


    public static final String SELLER_AUTH_CONTROLLER = SELLER_BASE_URL + "/sellerAuthController";





    //SignIn API's
    public static final String SELLER_SIGN_IN = "/sellerSignIn";

    public static final String SELLER_SEND_OTP = "/sellerSendOtp";

    public static final String VALIDATE_SELLER_OTP = "/validateSellerOtp";

    public static final String SELLER_SIGN_UP = "/sellerSignup";

    public static final String SELLER_TAX = "/sellerTax";




}
