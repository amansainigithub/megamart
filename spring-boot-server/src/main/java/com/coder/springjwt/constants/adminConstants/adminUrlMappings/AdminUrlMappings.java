package com.coder.springjwt.constants.adminConstants.adminUrlMappings;

public class AdminUrlMappings {


    //ADMIN MAPPING START
    //############## APP CONTEXT #####################
    public static final String APPLICATION_CONTEXT_PATH = "/shopping";

    //############## AUTH BASE URL #####################
    //Publically Allow
    public static final String AUTH_BASE_URL = APPLICATION_CONTEXT_PATH + "/api/admin/auth";

    //SignIn
    public static final String ADMIN_SIGN_IN = "/adminSignin";
    public static final String  ADMIN_PASS_KEY= "/adminPassKey";

    public static final String  ADMIN_SIGN_UP= "/adminSignUp";

    //ADMIN MAPPING ENDING


    //Protected URL's
    public static final String BASE_PROTECTED_URL = APPLICATION_CONTEXT_PATH + "/api/flying/v1";
    public static final String  PARAENT_CATEGORY= "/parentCategory";

    public static final String  SAVE_CHILD_CATEGORY= "/saveChildCategory";




}
