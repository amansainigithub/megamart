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

    public static final String SELLER_STORE_CONTROLLER = SELLER_PUBLIC_URL + "/sellerStoreController";



    //=======================================API's=========================================================
    //SignIn API's
    public static final String SELLER_SIGN_IN = "/sellerSignIn";
    public static final String STATE_CITY_PINCODE = "/stateCityPincode/{pincode}";
    public static final String SELLER_STORE = "/sellerStore";


//    =========================================CAtegory Controller======================================

    public static final String SELLER_PRODUCT_CATEGORY_CONTROLLER = SELLER_AUTH_URL + "/sellerProductCategoryController";
    public static final String GET_PARENT_CATEGORY_LIST = "/getParentCategory";
    public static final String GET_CHILD_CATEGORY_LIST_BY_ID = "/getChildCategoryListById/{parentId}";
    public static final String GET_BABY_CATEGORY_LIST_BY_CHILD_ID = "/getBabyCategoryListChildById/{childId}";
    public static final String GET_BORN_CATEGORY_LIST_BY_BABY_ID = "/getBornCategoryListByBabyId/{babyId}";
    public static final String GET_BORN_BY_ID = "/getBornById/{bornId}";


//    =====================================CATALOG CONTROLLER===================================
    public static final String SELLER_PRODUCT_CONTROLLER = SELLER_AUTH_URL + "/sellerProductController";
    public static final String GET_GST_LIST = "/getGstList";
    public static final String GET_PRODUCT_MASTERS = "/getProductMasters";
    public static final String FORM_BUILDER_FLYING = "/formBuilderFlying/{categoryId}";
    public static final String SAVE_SELLER_PRODUCT = "/saveSellerProduct/{bornCategoryId}";

    public static final String UPDATE_SELLER_PRODUCT = "/updateSellerProduct/{productId}";
    public static final String UPLOAD_PRODUCT_FILES = "/uploadProductFiles/{productLockerNumber}";
    public static final String GET_PRODUCT_COUNTS = "/getProductCounts";



//=====================================sellerProductStatusController===============================================
    public static final String SELLER_PRODUCT_STATUS_CONTROLLER = SELLER_AUTH_URL + "/sellerProductStatusController";
    public static final String GET_APPROVED_PRODUCT_LIST = "/getApprovedProductList/{username}";



    //Parent Category Created
    public static final String PARENT_CONTROLLER = SELLER_AUTH_URL +"/parentController";
    public static final String  CREATE_PARENT_CATEGORY= "/createParentCategory";
    public static final String  GET_PARENT_CATEGORY_LIST1= "/getParentCategoryList1";
    public static final String  DELETE_CATEGORY_BY_ID= "/deleteCategoryById/{categoryId}";
    public static final String  GET_PARENT_CATEGORY_BY_ID= "/getParentCategoryById/{categoryId}";
    public static final String  UPDATE_PARENT_CATEGORY= "/updateParentCategory";
    public static final String UPDATE_PARENT_CATEGORY_FILE="/updateParentCategoryFile/{parentCategoryId}";


    //CHILD URL's
    public static final String CHILD_CONTROLLER = SELLER_AUTH_URL +"/childController";

    public static final String  SAVE_CHILD_CATEGORY= "/saveChildCategory";
    public static final String  GET_CHILD_CATEGORY_LIST= "/getChildCategoryList";
    public static final String  DELETE_CHILD_CATEGORY_BY_ID= "/deleteChildCategoryById/{categoryId}";
    public static final String  GET_CHILD_CATEGORY_BY_ID= "/getChildCategoryById/{categoryId}";
    public static final String  UPDATE_CHILD_CATEGORY= "/updateChildCategory";
    public static final String UPDATE_CHILD_CATEGORY_FILE="/updateChildCategoryFile/{childCategoryId}";


    //BABY URL's
    public static final String BABY_CONTROLLER = SELLER_AUTH_URL +"/babyController";
    public static final String  SAVE_BABY_CATEGORY= "/saveBabyCategory";
    public static final String  GET_BABY_CATEGORY_LIST= "/getBabyCategoryList";
    public static final String  DELETE_BABY_CATEGORY_BY_ID= "/deleteBabyCategoryById/{categoryId}";
    public static final String  UPDATE_BABY_CATEGORY= "/updateBabyCategory";
    public static final String  GET_BABY_CATEGORY_BY_ID= "/getBabyCategoryById/{categoryId}";
    public static final String UPDATE_BABY_CATEGORY_FILE="/updateBabyCategoryFile/{babyCategoryId}";


    //BORN URL's
    public static final String BORN_CONTROLLER = SELLER_AUTH_URL +"/bornController";
    public static final String  SAVE_BORN_CATEGORY= "/saveBornCategory";
    public static final String  GET_BORN_CATEGORY_LIST= "/getBornCategoryList";
    public static final String  DELETE_BORN_CATEGORY_BY_ID= "/deleteBornCategoryById/{categoryId}";
    public static final String  UPDATE_BORN_CATEGORY= "/updateBornCategory";
    public static final String  GET_BORN_CATEGORY_BY_ID= "/getBornCategoryById/{categoryId}";
    public static final String UPDATE_BORN_CATEGORY_FILE="/updateBornCategoryFile/{bornCategoryId}";
    public static final String GET_BORN_CATEGORY_LIST_BY_PAGINATION="/getBornCategoryListByPagination";

    public static final String PRODUCT_SAMPLE_FILES="/productSampleFiles/{bornCategoryId}";



    //HSL URL's and Controller
    public static final String PRODUCT_HSN_CONTROLLER = SELLER_AUTH_URL +"/productHsnController";
    public static final String  SAVE_HSN= "/saveHsn";
    public static final String  DELETE_HSN= "/deleteHsnCode/{hsnCodeId}";
    public static final String  GET_HSN_CODE_BY_ID= "/getHsnCodeById/{hsnCodeId}";
    public static final String  UPDATE_HSN_CODE= "/updateHsnCode";
    public static final String GET_HSN_LIST_BY_PAGINATION="/getHsnListByPagination";



    //Size URL's and Controller
    public static final String PRODUCT_SIZE_CONTROLLER = SELLER_AUTH_URL +"/productSizeVariantController";
    public static final String  SAVE_SIZE= "/saveSize";
    public static final String  DELETE_SIZE= "/deleteSize/{sizeId}";
    public static final String  GET_SIZE_BY_ID= "/getSizeById/{sizeId}";
    public static final String  UPDATE_SIZE= "/updateSize";
    public static final String GET_SIZE="/getSize";


    //PRODUCT BRAND URL's and Controller
    public static final String PRODUCT_BRAND_CONTROLLER = SELLER_AUTH_URL +"/productBrandController";
    public static final String  SAVE_BRAND= "/saveBrand";
    public static final String  DELETE_BRAND= "/deleteBrand/{brandId}";
    public static final String  GET_BRAND_BY_ID= "/getBrandById/{brandId}";
    public static final String  UPDATE_BRAND= "/updateBrand";
    public static final String  GET_BRAND="/getBrand";


    //PRODUCT MATERIAL URL's and Controller
    public static final String PRODUCT_MATERIAL_CONTROLLER = SELLER_AUTH_URL +"/productMaterialController";
    public static final String  SAVE_MATERIAL= "/saveMaterial";
    public static final String  DELETE_MATERIAL= "/deleteMaterial/{materialId}";
    public static final String  GET_MATERIAL_BY_ID= "/getMaterialById/{materialId}";
    public static final String  UPDATE_MATERIAL= "/updateMaterial";
    public static final String GET_MATERIAL="/getMaterial";


    //PRODUCT TYPE URL's and Controller
    public static final String PRODUCT_TYPE_CONTROLLER = SELLER_AUTH_URL +"/productTypeController";
    public static final String  SAVE_TYPE= "/saveType";
    public static final String  DELETE_TYPE= "/deleteType/{typeId}";
    public static final String  GET_TYPE_BY_ID= "/getTypeById/{typeId}";
    public static final String  UPDATE_TYPE= "/updateType";
    public static final String GET_TYPE="/getType";

    //CATALOG NET_QUANTITY URL's and Controller
    public static final String PRODUCT_NET_QUANTITY_CONTROLLER = SELLER_AUTH_URL +"/productNetQuantityController";
    public static final String  SAVE_NET_QUANTITY= "/saveNetQuantity";
    public static final String  DELETE_NET_QUANTITY= "/deleteNetQuantity/{netQuantityId}";
    public static final String  GET_NET_QUANTITY_ID= "/getNetQuantityById/{netQuantityId}";
    public static final String  UPDATE_NET_QUANTITY= "/updateNetQuantity";
    public static final String GET_NET_QUANTITY="/getNetQuantity";




    //Catalog Weight Controller
    public static final String PRODUCT_WEIGHT_CONTROLLER = SELLER_AUTH_URL +"/productWeightController";

    public static final String  SAVE_WEIGHT= "/saveWeight";
    public static final String GET_WEIGHT="/getWeight";


    //Catalog Weight Controller
    public static final String PRODUCT_LENGTH_CONTROLLER = SELLER_AUTH_URL +"/productLengthController";
    public static final String  SAVE_LENGTH= "/saveLength";
    public static final String GET_LENGTH="/getLength";


    //Catalog Weight Controller
    public static final String PRODUCT_BREATH_CONTROLLER = SELLER_AUTH_URL +"/productBreathController";
    public static final String  SAVE_BREATH= "/saveBreath";
    public static final String GET_BREATH="/getBreath";


    //Catalog Weight Controller
    public static final String PRODUCT_HEIGHT_CONTROLLER = SELLER_AUTH_URL +"/productHeightController";
    public static final String  SAVE_HEIGHT= "/saveHeight";
    public static final String GET_HEIGHT="/getHeight";



    //CATALOG BRAND URL's and Controller
    public static final String SELLER_PRODUCT_VERIFY_CONTROLLER = SELLER_AUTH_URL +"/sellerProductVerifyController";
    public static final String  FORM_BUILDER_FLYING_BY_ADMIN= "/formBuilderFlyingByAdmin/{categoryId}";
    public static final String  GET_SELLER_PRODUCT_BY_ID_ADMIN= "/getSellerProductByIdAdmin/{productId}";
    public static final String  GET_SELLER_PRODUCT_UNDER_REVIEW_NO_VARIANT_LIST= "/getSellerProductUnderReviewNoVariantList/{username}";




    public static final String  SAVE_PRODUCT_REVIEW_STATUS= "/saveProductReviewStatus";
    public static final String  DELETE_PRODUCT_REVIEW_STATUS= "/deleteProductReviewStatus/{id}";
    public static final String  GET_PRODUCT_REVIEWS= "/getProductReviews";
    public static final String  GET_PRODUCT_REVIEW_STATUS_BY_ID= "/getProductReviewStatusById/{id}";
    public static final String  UPDATE_PRODUCT_REVIEW_STATUS= "/updateProductReviewStatus";
    public static final String  GET_PRODUCT_REVIEWS_STATUS_LIST= "/getProductReviewsStatusList";


    public static final String PRODUCT_REVIEW_DECISION_CONTROLLER = SELLER_AUTH_URL +"/productReviewDecisionController";
    public static final String  SAVE_PRODUCT_REVIEW_DECISION= "/saveProductReviewDecision";






//Home Slider Controller
    public static final String HOME_SLIDER_CONTROLLER = SELLER_AUTH_URL +"/homeSliderController";
    public static final String  SAVE_HOME_SLIDER= "/saveHomeSlider";
    public static final String UPDATE_HOME_SLIDER_FILE="/updateHomeSliderFile/{homeSliderId}";
    public static final String DELETE_HOME_SLIDER="/deleteHomeSlider/{homeSliderId}";
    public static final String  GET_HOME_SLIDER_BY_ID= "/getHomeSliderById/{homeSliderId}";
    public static final String  GET_HOME_SLIDER_LIST= "/getHomeSliderList";
    public static final String  UPDATE_HOME_SLIDER= "/updateHomeSlider";

    //Hot Deals Engine Controller
    public static final String HOT_DEALS_ENGINE_CONTROLLER = SELLER_AUTH_URL +"/hotDealsEngineController";
    public static final String  SAVE_HOT_DEALS_ENGINE= "/saveHotDealsEngine";
    public static final String  GET_HOT_DEALS_ENGINE= "/getHotDealsEngine/{engineId}";
    public static final String  GET_HOT_DEALS_ENGINES= "/getHotDealsEngines";
    public static final String DELETE_HOT_DEALS_ENGINES="/deleteHotDealsEngine/{engineId}";
    public static final String  UPDATE_HOT_DEALS_ENGINES= "/updateHotDealsEngine";




    //Hot Deals Controller
    public static final String HOT_DEALS_CONTROLLER = SELLER_AUTH_URL +"/hotDealsController";
    public static final String  SAVE_HOT_DEALS= "/saveHotDeals";
    public static final String DELETE_HOT_DEAL="/deleteHotDeals/{id}";
    public static final String DELETE_ALL_HOT_DEALS="/deleteAllHotDeals";
    public static final String  GET_HOT_DEALS= "/getHotDeals";
    public static final String  GET_HOT_DEAL= "/getHotDeal/{id}";
    public static final String  UPDATE_HOT_DEALS= "/updateHotDeals";
    public static final String UPDATE_HOT_DEAL_FILE="/updateHotDealFile/{dealId}";


    //Hot Deals Controller
    public static final String SELLER_ORDERS_CONTROLLER = SELLER_AUTH_URL +"/sellerOrderController";
    public static final String GET_PENDING_ORDER_BY_SELLER="/getPendingOrderBySeller";
    public static final String GET_SHIPPED_ORDER_BY_SELLER="/getShippedOrderBySeller";
    public static final String GET_OUT_OF_DELIVERY_ORDER_BY_SELLER="/getOutOfDeliveryOrderBySeller";
    public static final String GET_DELIVERED_ORDER_BY_SELLER="/getDeliveredOrderBySeller";
    public static final String GET_RETURN_ORDER_DATA="/getReturnOrdersData";
    public static final String GET_EXCHANGE_ORDER_DATA="/getExchangeOrdersData";


    //Hot Deals Controller
    public static final String SELLER_ORDERS_ONE_BY_ONE_CONTROLLER = SELLER_AUTH_URL +"/sellerOrderOneByOneController";
    public static final String GET_PENDING_ORDER_ONE_BY_ONE_BY_SELLER="/getPendingOrderOneByOneBySeller";
    public static final String GET_SHIPPED_ORDER_ONE_BY_ONE_BY_SELLER="/getShippedOrderOneByOneBySeller";
    public static final String GET_OUT_OF_DELIVERY_ORDER_ONE_BY_ONE_BY_SELLER="/getOutOfDeliveryOrderOneByOneBySeller";
    public static final String GET_DELIVERED_ORDER_ONE_BY_ONE_BY_SELLER="/getDeliveredOrderOneByOneBySeller";




    //Delivery Status Controller
    public static final String DELIVERY_STATUS_CONTROLLER = SELLER_AUTH_URL +"/deliveryStatusController";
    public static final String UPDATE_PENDING_DELIVERY_STATUS="/updatePendingDeliveryStatus";
    public static final String GET_DELIVERY_DETAILS_BY_ID="/getDeliveryDetailsById/{id}";
    public static final String AWB_NUMBER_MAPPING="/awbNumberMapping/{id}";





    //Seller Cancel Orders Controller
    public static final String SELLER_CANCEL_ORDER_CONTROLLER = SELLER_AUTH_URL +"/sellerCancelOrderController";
    public static final String  SELLER_CANCEL_ORDERS_FETCH= "/sellerCancelOrdersFetch";
    public static final String  SELLER_ORDER_REFUND_REQUEST= "/sellerOrderRefundRequest";
    public static final String  SELLER_CANCEL_ORDERS= "/sellerCancelOrders/{id}";



    //return Orders Controller
    public static final String RETURN_EXCHANGE_ORDER_CONTROLLER = SELLER_AUTH_URL +"/returnExchangeOrderController";
    public static final String EXCHANGE_DELIVERY_STATUS="/exchangeDeliveryStatus/{id}/{exchangeDeliveryStatus}";
    public static final String EXCHANGE_PICKUP_DATE_TIME="/exchangePickupDateTime/{id}/{pickupDateTime}";





    //HOUT_OF_STOCK_CONTROLLER
    public static final String OUT_OF_STOCK_PRODUCTS_CONTROLLER = SELLER_AUTH_URL +"/outOfStockProductsController";
    public static final String  GET_OUT_OF_STOCK_PRODUCTS= "/getOutOfStockProducts";
    public static final String  UPDATE_OUT_OF_STOCK_PRODUCTS= "/updateOutOfStocksProducts/{id}/{productInventory}";


}
