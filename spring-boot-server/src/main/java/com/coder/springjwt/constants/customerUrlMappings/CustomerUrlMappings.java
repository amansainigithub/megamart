package com.coder.springjwt.constants.customerUrlMappings;

public class CustomerUrlMappings {

    //ADMIN MAPPING START
    //############## APP CONTEXT #####################
    public static final String APPLICATION_CONTEXT_PATH = "/customer";

    //############## AUTH BASE URL #####################
    public static final String CUSTOMER_BASE_URL = APPLICATION_CONTEXT_PATH + "/api/v1";


    public static final String CUSTOMER_SIGN_IN = "/customerSignIn";

    public static final String CUSTOMER_SIGN_UP= "/customerSignUp";

    public static final String VERIFY_MOBILE_OTP = "/verifyMobileOtp";


    public static final String SAVE_FRESH_USER = "/saveFreshUser";

    public static final String GET_FRESH_USER = "/getFreshUser";

    public static final String VERIFY_FRESH_USER_MOBILE_OTP = "/verifyFreshUserMobileOtp";


}