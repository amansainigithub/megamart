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



    //    ####################### CATEGORY CONTROLLER #######################

    public static final String CATEGORY_CONTROLLER= CUSTOMER_BASE_URL + "/categoryController";
    public static final String GET_PRODUCT_CATEGORY= "/getProductCategory";
    public static final String GET_PRODUCT_LIST_BY_CATEGORY_ID= "/getProductListByCategoryId";
    public static final String GET_PRODUCT_LIST_BY_BORN_CATEGORY_ID= "/getProductListByBornCategoryId";
    public static final String PRODUCT_WATCHING= "/productWatching";

    public static final String PRODUCT_SEARCHING= "/productSearching";

    public static final String PRODUCT_FILTER= "/productFilter";
    public static final String GET_PRODUCT_BY_ID_CUSTOMER= "/getProductByIdCustomer";




    //    #######################RAZORPAY#######################
    public static final String PAYMENT_CONTROLLER= CUSTOMER_BASE_URL + "/paymentController";
    //ONLINE PAYING
    public static final String CREATE_ORDER= "/createOrder";
    //ONLINE PAYING
    public static final String ORDER_UPDATE= "/orderUpdate";
    //PAY COD
    public static final String PAY_COD= "/payCod";


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



    //    #######################ORDER CONTROLLER#######################
    public static final String ORDER_CONTROLLER= CUSTOMER_BASE_URL + "/orderController";
    public static final String GET_CUSTOMER_ORDERS= "/getCustomerOrders/{id}";
    public static final String GET_CUSTOMER_ORDERS_BY_ID= "/getCustomerOrdersById/{id}";
    public static final String GET_MY_ORDERS_DELIVERED= "/getMyOrdersDelivered/{id}";
    public static final String ORDER_RETURN_REQUEST_INITIATE= "/orderReturnRequestInitiate";
    public static final String ORDER_EXCHANGE_REQUEST_INITIATE= "/orderExchangeRequestInitiate";




    //    ####################### PROFILE CONTROLLER #######################
    public static final String PROFILE_CONTROLLER= CUSTOMER_BASE_URL + "/profileController";
    public static final String GET_PROFILE= "/getProfile/{id}";

    public static final String UPDATE_CUSTOMER_PROFILE= "/updateCustomerProfile";

    public static final String RESEND_EMAIL_LINK= "/resendEmailLink/{id}";


    //    #######################ORDER CONTROLLER#######################
    public static final String EMAIL_LINK_VERIFIER= CUSTOMER_BASE_URL + "/authToken";
    public static final String AUTH_TOKEN_VERIFIER=  "/authTokenVerifier";



    //    #######################ORDER CONTROLLER#######################
    public static final String PRODUCT_REVIEWS_CONTROLLER= CUSTOMER_BASE_URL + "/productReviewsController";
    public static final String UNREVIEWED_DELIVERED_PRODUCT = "/unReviewDeliveredProduct";
    public static final String SUBMIT_PRODUCT_REVIEW = "/submitProductReview";
    public static final String GET_USER_REVIEWS = "/getUserReviews";
    public static final String GET_EDIT_REVIEW = "/getEditReview/{reviewId}";




    //    #######################CANCLE ORDER CONTROLLER#######################
    public static final String ORDER_CANCEL_CONTROLLER= CUSTOMER_BASE_URL + "/orderCancelController";

    public static final String ORDER_CANCEL = "/orderCancel";

    public static final String GET_CANCEL_ORDERS= "/getCancelOrders/{id}";

}