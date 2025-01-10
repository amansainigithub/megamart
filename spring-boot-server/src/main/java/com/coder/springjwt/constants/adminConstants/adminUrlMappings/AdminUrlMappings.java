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

    public static final String PRODUCT_SAMPLE_FILES="/productSampleFiles/{bornCategoryId}";

    //FETCH USER's
    public static final String USERS_CONTROLLER = ADMIN_AUTHORIZE_URL +"/usersController";
    public static final String GET_CUSTOMER_BY_PAGINATION="/getCustomerByPagination";
    public static final String GET_SELLER_BY_PAGINATION="/getSellerByPagination";
    public static final String GET_ADMIN_BY_PAGINATION="/getAdminByPagination";



    //HSL URL's and Controller
    public static final String PRODUCT_HSN_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productHsnController";
    public static final String  SAVE_HSN= "/saveHsn";
    public static final String  DELETE_HSN= "/deleteHsnCode/{hsnCodeId}";
    public static final String  GET_HSN_CODE_BY_ID= "/getHsnCodeById/{hsnCodeId}";
    public static final String  UPDATE_HSN_CODE= "/updateHsnCode";
    public static final String GET_HSN_LIST_BY_PAGINATION="/getHsnListByPagination";



    //Size URL's and Controller
    public static final String PRODUCT_SIZE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productSizeVariantController";
    public static final String  SAVE_SIZE= "/saveSize";
    public static final String  DELETE_SIZE= "/deleteSize/{sizeId}";
    public static final String  GET_SIZE_BY_ID= "/getSizeById/{sizeId}";
    public static final String  UPDATE_SIZE= "/updateSize";
    public static final String GET_SIZE="/getSize";


    //PRODUCT MATERIAL URL's and Controller
    public static final String PRODUCT_MATERIAL_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productMaterialController";
    public static final String  SAVE_MATERIAL= "/saveMaterial";
    public static final String  DELETE_MATERIAL= "/deleteMaterial/{materialId}";
    public static final String  GET_MATERIAL_BY_ID= "/getMaterialById/{materialId}";
    public static final String  UPDATE_MATERIAL= "/updateMaterial";
    public static final String GET_MATERIAL="/getMaterial";


    //PRODUCT TYPE URL's and Controller
    public static final String PRODUCT_TYPE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productTypeController";
    public static final String  SAVE_TYPE= "/saveType";
    public static final String  DELETE_TYPE= "/deleteType/{typeId}";
    public static final String  GET_TYPE_BY_ID= "/getTypeById/{typeId}";
    public static final String  UPDATE_TYPE= "/updateType";
    public static final String GET_TYPE="/getType";


    //PRODUCT BRAND URL's and Controller
    public static final String PRODUCT_BRAND_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productBrandController";
    public static final String  SAVE_BRAND= "/saveBrand";
    public static final String  DELETE_BRAND= "/deleteBrand/{brandId}";
    public static final String  GET_BRAND_BY_ID= "/getBrandById/{brandId}";
    public static final String  UPDATE_BRAND= "/updateBrand";
    public static final String  GET_BRAND="/getBrand";


    //CATALOG NET_QUANTITY URL's and Controller
    public static final String PRODUCT_NET_QUANTITY_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productNetQuantityController";
    public static final String  SAVE_NET_QUANTITY= "/saveNetQuantity";
    public static final String  DELETE_NET_QUANTITY= "/deleteNetQuantity/{netQuantityId}";
    public static final String  GET_NET_QUANTITY_ID= "/getNetQuantityById/{netQuantityId}";
    public static final String  UPDATE_NET_QUANTITY= "/updateNetQuantity";
    public static final String GET_NET_QUANTITY="/getNetQuantity";



    //GSTPercentage Controller
    public static final String GST_PERCENTAGE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/gstPercentageController";
    public static final String  SAVE_GST_PERCENTAGE= "/saveGstPercentage";
    public static final String GET_GST_PERCENTAGE="/getGstPercentage";




    //Catalog Weight Controller
    public static final String PRODUCT_WEIGHT_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productWeightController";

    public static final String  SAVE_WEIGHT= "/saveWeight";
    public static final String GET_WEIGHT="/getWeight";


    //Catalog Weight Controller
    public static final String PRODUCT_LENGTH_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productLengthController";
    public static final String  SAVE_LENGTH= "/saveLength";
    public static final String GET_LENGTH="/getLength";


    //Catalog Weight Controller
    public static final String PRODUCT_BREATH_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productBreathController";
    public static final String  SAVE_BREATH= "/saveBreath";
    public static final String GET_BREATH="/getBreath";


    //Catalog Weight Controller
    public static final String PRODUCT_HEIGHT_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productHeightController";
    public static final String  SAVE_HEIGHT= "/saveHeight";
    public static final String GET_HEIGHT="/getHeight";



    //CATALOG BRAND URL's and Controller
    public static final String ADMIN_SELLER_PRODUCT_VERIFY_CONTROLLER = ADMIN_AUTHORIZE_URL +"/adminSellerProductVerifyController";
    public static final String  FORM_BUILDER_FLYING_BY_ADMIN= "/formBuilderFlyingByAdmin/{categoryId}";
    public static final String  GET_SELLER_PRODUCT_BY_ID_ADMIN= "/getSellerProductByIdAdmin/{productId}";
    public static final String  GET_SELLER_PRODUCT_VERIFY_LIST= "/getSellerProductVerifyList/{username}";
    public static final String  GET_SELLER_PRODUCT_UNDER_REVIEW_LIST= "/getSellerProductUnderReviewList/{username}";
    public static final String  GET_SELLER_PRODUCT_UNDER_REVIEW_NO_VARIANT_LIST= "/getSellerProductUnderReviewNoVariantList/{username}";


    //Product Reviews Status Controller
    public static final String ADMIN_PRODUCT_STATUS_REVIEW_CONTROLLER = ADMIN_AUTHORIZE_URL +"/adminProductReviewStatusController";
    public static final String  SAVE_PRODUCT_REVIEW_STATUS= "/saveProductReviewStatus";
    public static final String  DELETE_PRODUCT_REVIEW_STATUS= "/deleteProductReviewStatus/{id}";
    public static final String  GET_PRODUCT_REVIEWS= "/getProductReviews";
    public static final String  GET_PRODUCT_REVIEW_STATUS_BY_ID= "/getProductReviewStatusById/{id}";
    public static final String  UPDATE_PRODUCT_REVIEW_STATUS= "/updateProductReviewStatus";
    public static final String  GET_PRODUCT_REVIEWS_STATUS_LIST= "/getProductReviewsStatusList";
    public static final String  GET_SELLER_PRODUCT_APPROVED_LIST= "/getSellerProductApprovedList/{username}";

    public static final String  GET_SELLER_VARIANT_PRODUCT_APPROVED_LIST= "/getSellerVariantProductApprovedList/{username}";




    public static final String ADMIN_PRODUCT_REVIEW_DECISION_CONTROLLER = ADMIN_AUTHORIZE_URL +"/adminProductReviewDecisionController";
    public static final String  SAVE_PRODUCT_REVIEW_DECISION= "/saveProductReviewDecision";


}
