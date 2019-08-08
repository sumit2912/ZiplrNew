package com.mage.ziplrdelivery.model.param;

import android.text.TextUtils;
import android.util.Patterns;

import com.mage.ziplrdelivery.utils.Utils;

import java.io.Serializable;

public class RegistrationParamBean implements Serializable {
    private String name;
    private String email;
    private String password;
    private String confirm_password;
    private String country_code;
    private String phone_number;

    public RegistrationParamBean(String name, String email, String password, String confirm_password, String country_code, String phone_number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.country_code = country_code;
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int isValidData() {
        if (TextUtils.isEmpty(getName()))
            return 0;
        if (TextUtils.isEmpty(getEmail()))
            return 1;
        if (!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches())
            return 1;
        if (TextUtils.isEmpty(getPassword()))
            return 2;
        if (TextUtils.isEmpty(getConfirm_password()))
            return 3;
        if (!getPassword().equals(getConfirm_password()))
            return 4;
        if (TextUtils.isEmpty(getCountry_code()))
            return 5;
        if (TextUtils.isEmpty(getPhone_number()))
            return 6;
        if (!(getPhone_number().length()==10))
            return 7;
        return -1;
    }

    public String printParams() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name = " + (getName() != null ? getName() : ""));
        stringBuilder.append("  email = " + (getEmail() != null ? getEmail() : ""));
        stringBuilder.append("  password = " + (getPassword() != null ? getPassword() : ""));
        stringBuilder.append("  confirm_password = " + (getConfirm_password() != null ? getConfirm_password() : ""));
        stringBuilder.append("  country_code = " + (getCountry_code() != null ? getCountry_code() : ""));
        stringBuilder.append("  phone_number = " + (getPhone_number() != null ? getPhone_number() : ""));
        Utils.print("registrationParamBean",String.valueOf(stringBuilder));
        return String.valueOf(stringBuilder);
    }
}
