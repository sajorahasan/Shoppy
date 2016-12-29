package com.sajorahasan.shoppy;

/**
 * Created by Sajora on 10-12-2016.
 */

public class Constants {

    // change this access similar with accesskey in admin panel for security reason
    static String AccessKey = "12345";

    // API URL configuration
    public static final String AdminPageURL = "http://192.168.0.104/shoppy/";
    public static final String BASE_URL_APP = "http://192.168.0.104/shoppy/api/";

    public static final String BASE_URL = "http://192.168.0.104/";
    public static final String REGISTER_OPERATION = "register";
    public static final String LOGIN_OPERATION = "login";
    public static final String CHANGE_PASSWORD_OPERATION = "chgPass";

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String IS_LOGGED_IN = "isLoggedIn";

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String UNIQUE_ID = "unique_id";

    public static final String RESET_PASSWORD_INITIATE = "resPassReq";
    public static final String RESET_PASSWORD_FINISH = "resPass";

    public static final String TAG = "shoppy";

    public static final String CategoryAPI = "get-all-category-data.php";
    public static final String ProductAPI = "get-menu-data-by-category-id.php";
    public static final String ProductDetailAPI = "get-menu-detail.php";
    public static final String SEARCH_PRODUCTS = "searchProducts.php";

    //Keys for SharedPreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "shoppy";

    //We will use this to store the boolean in sharedPreference to track user is loggedIn or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedIn";

}
