package com.mage.ziplrdelivery.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mage.ziplrdelivery.utils.Utils;

import java.io.Serializable;

public class Result implements Serializable {
    //SignUp Response=============================================================================Start============================
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("password")
    @Expose
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void printVerifyOtpParams() {
        Utils.print("VerifyOtpParams", "phone_number = " + getPhoneNumber() + "  otp = " + getOtp() + "  password = " + getPassword());
    }
    //SignUp Response===============================================================================End====================
}
