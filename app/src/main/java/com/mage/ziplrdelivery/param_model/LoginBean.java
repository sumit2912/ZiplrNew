package com.mage.ziplrdelivery.param_model;

import android.content.Context;
import android.text.TextUtils;

import com.mage.ziplrdelivery.utils.Utils;

public class LoginBean {
    private static LoginBean loginBean;
    private String phone_number;
    private String password;
    private Context context;

    public LoginBean(Context context) {
        this.context = context;
    }

    public LoginBean(String phone_number, String password) {
        this.phone_number = phone_number;
        this.password = password;
    }

    public static LoginBean getInstance(Context context) {
        if (loginBean == null) {
            loginBean = new LoginBean(context);
        }
        return loginBean;
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

    public int isValidData() {
        if (TextUtils.isEmpty(getPhone_number()))
            return 0;
        if (getPhone_number().length()>9 && getPhone_number().length()<11)
            return 1;
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
        loginBean.setPhone_number("");
        loginBean.setPassword("");
    }

}
