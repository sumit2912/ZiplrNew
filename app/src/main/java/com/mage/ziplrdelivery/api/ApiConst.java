package com.mage.ziplrdelivery.api;

import java.util.ArrayList;
import java.util.List;

public class ApiConst {

    public enum API_RESULT {
        SUCCESS, FAIL
    }

    public static final String STATUS = "status";
    public static final String MESSAGE = "message";

    public static final String API_HOST = "http://demo.magespider.com/Ziplr/api/";
    public static final String SIGN_UP = "auth/signup";
    public static final String VERIFY_OTP = "auth/verify/otp";
    public static final String SEND_OTP = "auth/send/otp";
    public static final String PHONE_CHECK = "auth/check";
    public static final String LOGIN = "auth/login";
    public static final String FORGOT_PASSWORD = "auth/password/forgot";
    public static final String LOGOUT = "auth/logout";
    public static final String GET_USER = "auth/user";
    public static final String USER_UPDATE = "auth/user/update";
    public static final String USER_AVATAR_UPDATE = "auth/user/avatar";
    public static final String CHANGE_PASSWORD = "auth/password/change";
    public static final String CMS_PAGE = "api/auth/page";

    public static List<String> getNonBearerApis(){
        List<String> list = new ArrayList<>();
        list.add(SIGN_UP);
        list.add(VERIFY_OTP);
        list.add(SEND_OTP);
        list.add(PHONE_CHECK);
        list.add(LOGIN);
        list.add(FORGOT_PASSWORD);
        return list;
    }
}
