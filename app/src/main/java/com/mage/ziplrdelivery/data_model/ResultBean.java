package com.mage.ziplrdelivery.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mage.ziplrdelivery.utils.constant.ApiConst;

public class ResultBean {
    @SerializedName(ApiConst.STATUS)
    @Expose
    private int status;

    @SerializedName(ApiConst.MESSAGE)
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
