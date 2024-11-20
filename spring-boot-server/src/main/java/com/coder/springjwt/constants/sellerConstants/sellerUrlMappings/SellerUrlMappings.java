package com.coder.springjwt.constants.sellerConstants.sellerUrlMappings;

public class SellerUrlMappings {

    //ADMIN MAPPING START
    //############## APP CONTEXT #####################
    public static final String APPLICATION_CONTEXT_PATH = "/shopping";

    //############## NOT-AUTH BASE URL #####################
    public static final String SELLER_PUBLIC_URL = APPLICATION_CONTEXT_PATH + "/api/seller/v1";

    public static final String SELLER_AUTH_URL = APPLICATION_CONTEXT_PATH + "/api/seller/fly/v1";



    //=======================================CONTROLLER=========================================================

    public static final String SELLER_AUTH_CONTROLLER = SELLER_PUBLIC_URL + "/sellerAuthController";

    public static final String STATE_CITY_PINCODE_CONTROLLER = SELLER_PUBLIC_URL + "/stateCityPincodeController";

    public static final String SELLER_TAX_CONTROLLER = SELLER_PUBLIC_URL + "/sellerTaxController";

    public static final String SELLER_PICKUP_CONTROLLER = SELLER_PUBLIC_URL + "/sellerPickupController";

    public static final String SELLER_BANK_CONTROLLER = SELLER_PUBLIC_URL + "/sellerBankController";

    public static final String SELLER_STORE_CONTROLLER = SELLER_PUBLIC_URL + "/sellerStoreController";



    //=======================================API's=========================================================

    //SignIn API's
    public static final String SELLER_SIGN_IN = "/sellerSignIn";

    public static final String SELLER_SEND_OTP = "/sellerSendOtp";

    public static final String VALIDATE_SELLER_OTP = "/validateSellerOtp";

    public static final String SELLER_SIGN_UP = "/sellerSignup";


    public static final String STATE_CITY_PINCODE = "/stateCityPincode/{pincode}";

    public static final String SELLER_TAX = "/sellerTax";

    //Seller pickup Data
    public static final String SELLER_PICKUP = "/sellerPickup";

    public static final String SELLER_BANK = "/sellerBank";

    public static final String SELLER_STORE = "/sellerStore";

    public static final String SELLER_SAVE_CATALOG = "/sellerSaveCatalog";

    public static final String SELLER_GET_CATALOG = "/getSellerCatalog/{catalogId}";





//    =========================================CAtegory Controller======================================

    public static final String SELLER_PRODUCT_CATEGORY_CONTROLLER = SELLER_AUTH_URL + "/sellerProductCategoryController";
    public static final String GET_PARENT_CATEGORY_LIST = "/getParentCategory";
    public static final String GET_CHILD_CATEGORY_LIST_BY_ID = "/getChildCategoryListById/{parentId}";
    public static final String GET_BABY_CATEGORY_LIST_BY_CHILD_ID = "/getBabyCategoryListChildById/{childId}";
    public static final String GET_BORN_CATEGORY_LIST_BY_BABY_ID = "/getBornCategoryListByBabyId/{babyId}";

    public static final String GET_BORN_BY_ID = "/getBornById/{bornId}";


    public static final String SELLER_CATALOG_CONTROLLER = SELLER_AUTH_URL + "/sellerCatalogController";




//    =====================================CATALOG CONTROLLER===================================

    public static final String GET_GST_LIST = "/getGstList";

    public static final String GET_CATALOG_MASTERS = "/getCatalogMasters";
    public static final String GET_ALL_CATALOG_BY_USERNAME = "/getAllCatalogByUsername";

    public static final String GET_ALL_CATALOG_BY_QC_PROGRESS = "/getAllCatalogByQcProgress";

    public static final String GET_ALL_CATALOG_BY_DRAFT = "/getAllCatalogByDraft";

    public static final String GET_ALL_CATALOG_BY_ERROR = "/getAllCatalogByError";

    public static final String GET_ALL_CATALOG_BY_QC_PASS = "/getAllCatalogByQcPass";





}
