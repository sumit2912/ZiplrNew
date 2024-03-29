package com.mage.ziplrdelivery.model.param;

import android.text.TextUtils;

import com.mage.ziplrdelivery.utils.Utils;

public class LoginParamBean {
    private String phone_number;
    private String password;

    public LoginParamBean() {
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
        if (!(getPhone_number().length() == 10))
            return 1;
        return -1;
    }

    public int isValidPassword() {
        if (TextUtils.isEmpty(getPassword()))
            return 0;
        return -1;
    }

    public void printParams() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("phone_number = " + (getPhone_number() != null ? getPhone_number() : ""));
        stringBuilder.append("  password = " + (getPassword() != null ? getPassword() : ""));
        Utils.print("LoginParamBean", String.valueOf(stringBuilder));
    }

    public void resetAll() {
        setPhone_number(null);
        setPassword(null);
    }
}
