package com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings;

public class CustomerUrlMappings {

    //ADMIN MAPPING START
    //############## APP CONTEXT #####################
    public static final String APPLICATION_CONTEXT_PATH = "/customer";

    //############## AUTH BASE URL #####################
    public static final String CUSTOMER_BASE_URL = APPLICATION_CONTEXT_PATH + "/api/v1";


    public static final String CUSTOMER_SIGN_IN = "/customerSignIn";

    public static final String CUSTOMER_SIGN_UP= "/customerSignUp";

    public static final String CUSTOMER_SIGN_UP_COMPLETED= "/customerSignUpCompleted";

    public static final String VERIFY_FRESH_USER_MOBILE_OTP = "/verifyFreshUserMobileOtp";

    public static final String CUSTOMER_FORGOT_PASSWORD= "/customerForgotPassword";

    public static final String CUSTOMER_FORGOT_PASSWORD_FINAL= "/customerForgotPasswordFinal";



//    #######################RAZORPAY#######################
    public static final String PAYMENT_CONTROLLER= CUSTOMER_BASE_URL + "/paymentController";
    public static final String CREATE_ORDER= "/createOrder";
    public static final String ORDER_UPDATE= "/orderUpdate";


    //    #######################DASHBOARD CONTROLLER#######################
    public static final String DASHBOARD_CONTROLLER= CUSTOMER_BASE_URL + "/dashboardController";
    public static final String GET_DASHBOARD= "/getDashboard/{username}";


    //    #######################SAVE CUSTOMER ADDRESS CONTROLLER#######################
    public static final String ADDRESS_CONTROLLER= CUSTOMER_BASE_URL + "/addressController";
    public static final String SAVE_ADDRESS= "/saveAddress";
    public static final String GET_ADDRESS= "/getAddress";
    public static final String DELETE_ADDRESS= "/deleteAddress/{id}";
    public static final String SET_DEFAULT_ADDRESS= "/setDefaultAddress/{id}";

    public static final String GET_ADDRESS_BY_ID= "/getAddressById/{id}";


    public static final String UPDATE_ADDRESS= "/updateAddress";


}