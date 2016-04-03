package com.templatexuv.apresh.customerapp.util;

/**
 * Created by Apresh on 6/29/2015.
 */
public class Constants {

    public static final int SOCKET_TIMEOUT = 90 * 1000;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static final int GET_REQUEST  = 1;
    public static final int POST_REQUEST = 2;
    public static final String TAG = "DISTRIBUTORAPP";
    // device height width
    /*public static int DEVICE_DISPLAY_WIDTH;
    public static int DEVICE_DISPLAY_HEIGHT;

    */public static boolean isnormalmode = true;

    public static final String PREF_IS_CATEGORIES_SET = "IS_CATEGORIES_SET";
    public static final String PREF_IS_LOGGED_IN = "IS_LOGGED_IN";
    //user preferences
    public static final String PREF_REMEMBER_ME = "rememberme";
    public static final String PREF_USER_ID = "_id";
    public static final String PREF_EMAILORPHONEVALUE = "name";
    public static final String PREF_PIN_VALUE = "pinvalue";
    public static final String PREF_USER_EMAIL = "email";
    public static final String PREF_USER_MOBILENUMBER = "mobilenumber";
    public static final String PREF_SELLER_ID = "seller_id";
    //url
    public static final String BASE_URL = "http://fantabazaar.com/eShopping/";

    public static final String URL_LOGIN = "user/validateUser";
    public static final String URL_REGISTRATION = "user/register";
    public static final String URL_USERNAME_CHECK = "seller/userNameCheck";
    public static final String URL_BRANDNAME_CHECK = "seller/brandNameCheck";

    public static final String URL_GET_ALL_CATEGORIES = "categories/getAllCategories";
    public static final String URL_GET_PRODUCTS_BY_CATEGORY = "products/getProductsByCategory";
    public static final String URL_GET_SELLERS = "seller/getAllActiveSeller";

    public static final String URL_GET_SELLERCOMMENTS = "sellerReview/getCommentsBySeller";

    public static final String URL_ADD_PRODUCT_TO_CART = "cart/addProductToCart";
    public static final String URL_GET_NO_OF_PRODUCTS = "cart/getNoOfProductsInCart";
    public static final String URL_DELETE_PRODUCT_FROM_CART = "cart/deleteProductFromCart";
    public static final String URL_VIEW_CART = "cart/viewCart";
    public static final String URL_CHECK_OUT_CART = "orders/checkAndLockOrder";

    public static final String URL_CONFIRM_ORDER = "orders/confirmOrder";
    public static final String URL_UNLOCK_PRODUCTS = "orders/unlockProduts";
    public static final String URL_GET_ORDERS = "orders/getOrdersByCustomer";


    public static final String URL_ADD_ADDRESS    = "address/addUserAddress";
    public static final String URL_GET_ADDRESS    = "address/getUserAddress";
    public static final String URL_EDIT_ADDRESS   = "address/editUserAddress";
    public static final String URL_DELETE_ADDRESS = "address/deleteUserAddress";


    public static final String URL_FORGOT_PASSWORD = "";
    public static final String URL_UPDATE_PASSWORD = "";

    public static final String URL_SUBMIT_PRODUCT = "products/addProductDetails";
    public static final String URL_SUBMIT_IMAGES = "products/addProductImages";
    public static final String URL_DELETE_PRODUCT = "products/deleteImage";

    public static final String URL_UPDATE_PRODUCT = "products/updateProductDetails";


    public static final String URL_PRODUCT_BY_SELLER = "products/getProductsBySeller";
    public static final String URL_ORDER = "";

    public static final String URL_GET_PROPERTYVALUES = "property/getPropertiesAndValues";

    public static final String URL_GET_PRODUCTS_BY_FILTER = "products/getProductsByFilter";




}
