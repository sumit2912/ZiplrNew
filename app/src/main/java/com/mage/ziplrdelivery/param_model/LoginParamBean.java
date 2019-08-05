package com.mage.ziplrdelivery.param_model;

import android.content.Context;
import android.text.TextUtils;

import com.mage.ziplrdelivery.utils.Utils;

public class LoginParamBean {
    private static LoginParamBean loginParamBean;
    private String phone_number;
    private String password;

    public LoginParamBean() {
    }

    public static LoginParamBean getInstance() {
        if (loginParamBean == null) {
            loginParamBean = new LoginParamBean();
        }
        return loginParamBean;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int isValidPhoneNumber() {
        if (TextUtils.isEmpty(getPhone_number()))
            return 0;
        if (!(getPhone_number().length()==10))
            return 1;
        return -1;
    }

    public int isValidPassword(){
        if (TextUtils.isEmpty(getPassword()))
            return 2;
        return -1;
    }

    public void printParams() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("phone_number = " + (getPhone_number() != null ? getPhone_number() : ""));
        stringBuilder.append("  password = " + (getPassword() != null ? getPassword() : ""));
        Utils.print("LoginParamBean",String.valueOf(stringBuilder));
    }

    public void resetAll() {
        loginParamBean.setPhone_number(null);
        loginParamBean.setPassword(null);
    }

}
