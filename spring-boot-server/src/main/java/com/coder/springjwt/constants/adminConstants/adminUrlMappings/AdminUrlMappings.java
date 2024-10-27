package com.coder.springjwt.constants.adminConstants.adminUrlMappings;

public class AdminUrlMappings {


    //ADMIN MAPPING START
    //############## APP CONTEXT #####################
    public static final String APPLICATION_CONTEXT_PATH = "/shopping";

    //############## AUTH BASE URL #####################
    //Publically Allow
    public static final String ADMIN_PUBLIC_URL = APPLICATION_CONTEXT_PATH + "/api/admin/auth";
    //Protected URL's
    public static final String ADMIN_AUTHORIZE_URL = APPLICATION_CONTEXT_PATH + "/api/admin/flying/v1";



    //SignIn Public URL's

    public static final String ADMIN_AUTH_CONTROLLER = ADMIN_PUBLIC_URL +"/adminAuthController";
    public static final String ADMIN_SIGN_IN = "/adminSignin";
    public static final String  ADMIN_PASS_KEY= "/adminPassKey";
    public static final String  ADMIN_SIGN_UP= "/adminSignUp";

    //ADMIN MAPPING ENDING




    public static final String PARENT_CONTROLLER = ADMIN_AUTHORIZE_URL +"/parentController";
    public static final String  CREATE_PARENT_CATEGORY= "/createParentCategory";
    public static final String  GET_PARENT_CATEGORY_LIST= "/getParentCategoryList";
    public static final String  DELETE_CATEGORY_BY_ID= "/deleteCategoryById/{categoryId}";
    public static final String  GET_PARENT_CATEGORY_BY_ID= "/getParentCategoryById/{categoryId}";
    public static final String  UPDATE_PARENT_CATEGORY= "/updateParentCategory";
    public static final String UPDATE_PARENT_CATEGORY_FILE="/updateParentCategoryFile/{parentCategoryId}";



    //CHILD URL's
    public static final String CHILD_CONTROLLER = ADMIN_AUTHORIZE_URL +"/childController";

    public static final String  SAVE_CHILD_CATEGORY= "/saveChildCategory";
    public static final String  GET_CHILD_CATEGORY_LIST= "/getChildCategoryList";
    public static final String  DELETE_CHILD_CATEGORY_BY_ID= "/deleteChildCategoryById/{categoryId}";
    public static final String  GET_CHILD_CATEGORY_BY_ID= "/getChildCategoryById/{categoryId}";
    public static final String  UPDATE_CHILD_CATEGORY= "/updateChildCategory";
    public static final String UPDATE_CHILD_CATEGORY_FILE="/updateChildCategoryFile/{childCategoryId}";



    //BABY URL's
    public static final String BABY_CONTROLLER = ADMIN_AUTHORIZE_URL +"/babyController";
    public static final String  SAVE_BABY_CATEGORY= "/saveBabyCategory";
    public static final String  GET_BABY_CATEGORY_LIST= "/getBabyCategoryList";
    public static final String  DELETE_BABY_CATEGORY_BY_ID= "/deleteBabyCategoryById/{categoryId}";
    public static final String  UPDATE_BABY_CATEGORY= "/updateBabyCategory";
    public static final String  GET_BABY_CATEGORY_BY_ID= "/getBabyCategoryById/{categoryId}";
    public static final String UPDATE_BABY_CATEGORY_FILE="/updateBabyCategoryFile/{babyCategoryId}";





    //BORN URL's
    public static final String BORN_CONTROLLER = ADMIN_AUTHORIZE_URL +"/bornController";
    public static final String  SAVE_BORN_CATEGORY= "/saveBornCategory";
    public static final String  GET_BORN_CATEGORY_LIST= "/getBornCategoryList";
    public static final String  DELETE_BORN_CATEGORY_BY_ID= "/deleteBornCategoryById/{categoryId}";
    public static final String  UPDATE_BORN_CATEGORY= "/updateBornCategory";
    public static final String  GET_BORN_CATEGORY_BY_ID= "/getBornCategoryById/{categoryId}";
    public static final String UPDATE_BORN_CATEGORY_FILE="/updateBornCategoryFile/{bornCategoryId}";
    public static final String GET_BORN_CATEGORY_LIST_BY_PAGINATION="/getBornCategoryListByPagination";



    //FETCH USER's
    public static final String USERS_CONTROLLER = ADMIN_AUTHORIZE_URL +"/usersController";
    public static final String GET_CUSTOMER_BY_PAGINATION="/getCustomerByPagination";
    public static final String GET_SELLER_BY_PAGINATION="/getSellerByPagination";
    public static final String GET_ADMIN_BY_PAGINATION="/getAdminByPagination";



    //HSL URL's and Controller
    public static final String HSN_CONTROLLER = ADMIN_AUTHORIZE_URL +"/hsnController";
    public static final String  SAVE_HSN= "/saveHsn";
    public static final String  DELETE_HSN= "/deleteHsnCode/{hsnCodeId}";
    public static final String  GET_HSN_CODE_BY_ID= "/getHsnCodeById/{hsnCodeId}";
    public static final String  UPDATE_HSN_CODE= "/updateHsnCode";
    public static final String GET_HSN_LIST_BY_PAGINATION="/getHsnListByPagination";



    //HSL URL's and Controller
    public static final String CATALOG_SIZE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/catalogSizeController";
    public static final String  SAVE_CATALOG_SIZE= "/saveCatalogSize";
    public static final String  DELETE_CATALOG_SIZE= "/deleteCatalogSize/{sizeId}";
    public static final String  GET_CATALOG_SIZE_BY_ID= "/getCatalogSizeById/{sizeId}";
    public static final String  UPDATE_CATALOG_SIZE= "/updateCatalogSize";
    public static final String GET_CATALOG_SIZE="/getCatalogSize";


    //CATALOG MATERIAL URL's and Controller
    public static final String CATALOG_MATERIAL_CONTROLLER = ADMIN_AUTHORIZE_URL +"/catalogMaterialController";
    public static final String  SAVE_CATALOG_MATERIAL= "/saveCatalogMaterial";
    public static final String  DELETE_CATALOG_MATERIAL= "/deleteCatalogMaterial/{materialId}";
    public static final String  GET_CATALOG_MATERIAL_BY_ID= "/getCatalogMaterialById/{materialId}";
    public static final String  UPDATE_CATALOG_MATERIAL= "/updateCatalogMaterial";
    public static final String GET_CATALOG_MATERIAL="/getCatalogMaterial";


    //CATALOG TYPE URL's and Controller
    public static final String CATALOG_TYPE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/catalogTypeController";
    public static final String  SAVE_CATALOG_TYPE= "/saveCatalogType";
    public static final String  DELETE_CATALOG_TYPE= "/deleteCatalogType/{typeId}";
    public static final String  GET_CATALOG_TYPE_BY_ID= "/getCatalogTypeById/{typeId}";
    public static final String  UPDATE_CATALOG_TYPE= "/updateCatalogType";
    public static final String GET_CATALOG_TYPE="/getCatalogType";


    //CATALOG BRAND URL's and Controller
    public static final String CATALOG_BRAND_CONTROLLER = ADMIN_AUTHORIZE_URL +"/catalogBrandController";
    public static final String  SAVE_CATALOG_BRANDE= "/saveCatalogBrand";
    public static final String  DELETE_BRAND= "/deleteBrand/{brandId}";
    public static final String  GET_CATALOG_BRAND_ID= "/getCatalogBrandById/{brandId}";
    public static final String  UPDATE_CATALOG_BRAND= "/updateCatalogBrand";
    public static final String GET_CATALOG_BRAND="/getCatalogBrand";


    //CATALOG NET_QUANTITY URL's and Controller
    public static final String CATALOG_NET_QUANTITY_CONTROLLER = ADMIN_AUTHORIZE_URL +"/catalogNetQuantityController";
    public static final String  SAVE_CATALOG_NET_QUANTITY= "/saveCatalogNetQuantity";
    public static final String  DELETE_NET_QUANTITY= "/deleteNetQuantity/{netQuantityId}";
    public static final String  GET_CATALOG_NET_QUANTITY_ID= "/getCatalogNetQuantityById/{netQuantityId}";
    public static final String  UPDATE_NET_QUANTITY= "/updateNetQuantity";
    public static final String GET_NET_QUANTITY="/getNetQuantity";
}
