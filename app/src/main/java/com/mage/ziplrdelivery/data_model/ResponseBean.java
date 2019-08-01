package com.mage.ziplrdelivery.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mage.ziplrdelivery.utils.constant.ApiConst;

import java.util.List;

public class ResponseBean {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
