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



    public static final String  CREATE_PARENT_CATEGORY= "/createParentCategory";

    public static final String  GET_PARENT_CATEGORY_LIST= "/getParentCategoryList";

    public static final String  DELETE_CATEGORY_BY_ID= "/deleteCategoryById/{categoryId}";

    public static final String  GET_PARENT_CATEGORY_BY_ID= "/getParentCategoryById/{categoryId}";

    public static final String  UPDATE_PARENT_CATEGORY= "/updateParentCategory";

    public static final String UPDATE_PARENT_CATEGORY_FILE="/updateParentCategoryFile/{parentCategoryId}";



    //CHILD URL's

    public static final String  SAVE_CHILD_CATEGORY= "/saveChildCategory";
    public static final String  GET_CHILD_CATEGORY_LIST= "/getChildCategoryList";
    public static final String  DELETE_CHILD_CATEGORY_BY_ID= "/deleteChildCategoryById/{categoryId}";
    public static final String  GET_CHILD_CATEGORY_BY_ID= "/getChildCategoryById/{categoryId}";
    public static final String  UPDATE_CHILD_CATEGORY= "/updateChildCategory";
    public static final String UPDATE_CHILD_CATEGORY_FILE="/updateChildCategoryFile/{childCategoryId}";



    //CHILD URL's

    public static final String  SAVE_BABY_CATEGORY= "/saveBabyCategory";
    public static final String  GET_BABY_CATEGORY_LIST= "/getBabyCategoryList";
    public static final String  DELETE_BABY_CATEGORY_BY_ID= "/deleteBabyCategoryById/{categoryId}";
    public static final String  UPDATE_BABY_CATEGORY= "/updateBabyCategory";
    public static final String  GET_BABY_CATEGORY_BY_ID= "/getBabyCategoryById/{categoryId}";
    public static final String UPDATE_BABY_CATEGORY_FILE="/updateBabyCategoryFile/{babyCategoryId}";





    //BORN URL's

    public static final String  SAVE_BORN_CATEGORY= "/saveBornCategory";
    public static final String  GET_BORN_CATEGORY_LIST= "/getBornCategoryList";
    public static final String  DELETE_BORN_CATEGORY_BY_ID= "/deleteBornCategoryById/{categoryId}";
    public static final String  UPDATE_BORN_CATEGORY= "/updateBornCategory";
    public static final String  GET_BORN_CATEGORY_BY_ID= "/getBornCategoryById/{categoryId}";
    public static final String UPDATE_BORN_CATEGORY_FILE="/updateBornCategoryFile/{bornCategoryId}";
    public static final String GET_BORN_CATEGORY_LIST_BY_PAGINATION="/getBornCategoryListByPagination";



    //FETCH CUSTOMER USER
    public static final String GET_CUSTOMER_BY_PAGINATION="/getCustomerByPagination";


}
