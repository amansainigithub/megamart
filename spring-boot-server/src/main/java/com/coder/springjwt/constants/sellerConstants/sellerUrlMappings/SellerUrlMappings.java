package com.coder.springjwt.constants.sellerConstants.sellerUrlMappings;

public class SellerUrlMappings {

    //ADMIN MAPPING START
    //############## APP CONTEXT #####################
    public static final String APPLICATION_CONTEXT_PATH = "/shopping";

    //############## NOT-AUTH BASE URL #####################
    public static final String SELLER_BASE_URL = APPLICATION_CONTEXT_PATH + "/api/seller/v1";


    public static final String SELLER_AUTH_CONTROLLER = SELLER_BASE_URL + "/sellerAuthController";

    public static final String STATE_CITY_PINCODE_CONTROLLER = SELLER_BASE_URL + "/stateCityPincodeController";

    public static final String SELLER_TAX_CONTROLLER = SELLER_BASE_URL + "/sellerTaxController";

    public static final String SELLER_PICKUP_CONTROLLER = SELLER_BASE_URL + "/sellerPickupController";

    public static final String SELLER_BANK_CONTROLLER = SELLER_BASE_URL + "/sellerBankController";

    public static final String SELLER_STORE_CONTROLLER = SELLER_BASE_URL + "/sellerStoreController";











    //SignIn API's
    public static final String SELLER_SIGN_IN = "/sellerSignIn";

    public static final String SELLER_SEND_OTP = "/sellerSendOtp";

    public static final String VALIDATE_SELLER_OTP = "/validateSellerOtp";

    public static final String SELLER_SIGN_UP = "/sellerSignup";

    public static final String SELLER_TAX = "/sellerTax";




    public static final String STATE_CITY_PINCODE = "/stateCityPincode/{pincode}";


    //Seller pickup Data
    public static final String SELLER_PICKUP = "/sellerPickup";

    public static final String SELLER_BANK = "/sellerBank";

    public static final String SELLER_STORE = "/sellerStore";





}
