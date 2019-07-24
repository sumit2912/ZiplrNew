package com.mage.ziplrdelivery.utils;

import android.app.Activity;

import java.util.HashMap;

public class Const {

    public static String APP_NAME = "Pulse";
    public static final String PREF_FILE = APP_NAME + "_PREF";
    public static HashMap<String, Activity> screen_al;

    public static final int INTENT_100 = 100;
    public static final int INTENT_200 = 200;
    public static final int INTENT_300 = 300;

    // Api Response
    public static enum API_RESULT {
        SUCCESS, FAIL
    }

    public static final String STATUS = "status";
    public static final String MESSAGE = "message";

    public static final String API_HOST = "http://demo.magespider.com/Pulse/api/";


    //PREF KEYS
    public static final String PREF_USER_ID = "pref_id";
    public static final String PREF_FIRST_NAME = "pref_firstname";
    public static final String PREF_LAST_NAME = "pref_lastname";
    public static final String PREF_EMAIL = "pref_email";
    public static final String PREF_AVATAR_URL = "pref_avatar_url";
    public static final String PREF_GENDER = "pref_gender";
    public static final String PREF_BIRTHYEAR = "pref_birthyear";
    public static final String PREF_ETHNIC_BACKGROUND = "pref_ethnic_background";
    public static final String PREF_USER_BUSINESS = "pref_user_business";
    public static final String PREF_ZIP_CODE = "pref_zipcode";

    public static final String PREF_BUSINESS_WEBSITE = "pref_business_website";
    public static final String PREF_COUNTRY_CODE = "country_code";
    public static final String PREF_BUSINESS_NAME = "pref_business_name";
    public static final String PREF_BUSINESS_TYPE = "pref_business_type";
    public static final String PREF_BUSINESS_PHONE = "pref_business_phone";
    public static final String PREF_BUSINESS_ADDRESS = "pref_business_address";
    public static final String PREF_BUSINESS_ID = "pref_business_id";
    public static final String PREF_BUSINESS_LOGO = "pref_business_logo";
    public static final String PREF_BUSINESS_COVER_LOGO = "pref_business_cover_logo";
    public static final String PREF_IS_NOTIFICATION = "pref_is_notification";

    public static final String PREF_BUSINESS_STATUS = "pref_business_status";
    public static final String PREF_USER_BUSINESS_TYPE = "pref_user_business_type";

    public static final String PREF_ROLE = "pref_role";

    public static final String PREF_ACCESS_TOKEN = "pref_access_token";
    public static final String PREF_TOKEN_TYPE = "pref_token_type";

    public static final String PREF_BUSINESS_PROFILE = "pref_business_profile";
    public static final String PREF_IS_ONLINE = "PREF_IS_ONLINE";




    //API TAG
    public static final String AUTH_SIGNUP = "auth/signup";
    public static final String AUTH_VERIFY_OTP = "auth/verify/otp";
    public static final String AUTH_LOGIN = "auth/login";
    public static final String AUTH_SEND_OTP = "auth/send/otp";
    public static final String AUTH_RESET_PASSWORD = "auth/password/reset";
    public static final String AUTH_LOGOUT = "auth/logout";
    public static final String AUTH_UPDATE_PROFILE = "auth/user/update";
    public static final String AUTH_UPDATE_PROFILE_IMAGE = "auth/user/avatar";
    public static final String AUTH_BUSINESS_SIGNUP = "auth/user/avatar";
    public static final String AUTH_BUSINESS_UPDATE_PROFILE = "auth/business/update";
    public static final String AUTH_GET_USER = "auth/user";
    public static final String AUTH_SOICAL_LOGIN = "auth/social/login";
    public static final String AUTH_GET_PAGE = "auth/page";
    public static final String AUTH_BUSINESS_TYPE = "auth/business/type";
    public static final String AUTH_BUSINESS_NAME = "auth/business/get";
    public static final String AUTH_ETHNIC_BG = "auth/ethnic";
    public static final String AUTH_BUSINESS_NEARBY_LIST = "auth/business/gets?page=1";
    public static final String AUTH_BUSINESS_DETAILS = "auth/business/detail";
    public static final String AUTH_UPDATE_BUSINESS_LOGO = "auth/business/logo";
    public static final String AUTH_UPDATE_BUSINESS_COVER_LOGO = "auth/business/logo";
    public static final String AUTH_REVIEW_QUESTION_LIST = "auth/business/review/quesion";
    public static final String AUTH_ADD_SURVEY_REVIEW = "auth/survey/review";
    public static final String AUTH_USER_NOTIFICATION = "auth/user/notification";
    public static final String AUTH_RATTING = "auth/ratting";
    public static final String AUTH_BUSINESS_EMPLOYEE = "auth/business/employee";
    public static final String AUTH_BUSINESS_STATUS = "auth/business/status";
    public static final String AUTH_BUSINESS_SURVEY_QUESTION = "auth/business/survey/question";
    public static final String AUTH_BUSINESS_SURVEY_QUESTION_DELETE = "auth/business/survey/question/delete";
    public static final String AUTH_BUSINESS_SURVEY_QUESTION_ADD = "auth/business/survey/question/add";
    public static final String AUTH_BUSINESS_SURVEY_QUESTION_UPDATE = "auth/business/survey/question/update";
    public static final String AUTH_GET_NOTIFICATION = "auth/getNotification";
    public static final String AUTH_GET_IMAGE_DELETE = "auth/business/image/delete";
    public static final String AUTH_GET_GRAPH_WEEK = "auth/graph/user/week";



}
