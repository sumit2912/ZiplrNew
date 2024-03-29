package com.mage.ziplrdelivery.api;

import com.mage.ziplrdelivery.MyApplication;
import com.mage.ziplrdelivery.model.data.ResponseBean;
import com.mage.ziplrdelivery.model.data.Result;
import com.mage.ziplrdelivery.prefmanager.PrefConst;
import com.mage.ziplrdelivery.prefmanager.PrefManager;
import com.mage.ziplrdelivery.utils.Utils;

public class ApiResponseHelper {
    private static final String TAG = "ApiResponseHelper";
    private PrefManager prefManager;
    private ResponseBean responseBean;

    public ApiResponseHelper(PrefManager prefManager) {
        this.prefManager = prefManager;
        responseBean = new ResponseBean();
    }

    public ResponseBean getResponseBean() {
        return responseBean;
    }

    public void setResponseBean(ResponseBean responseBean) {
        this.responseBean = responseBean;
        printLogs(this.responseBean);
    }

    private void printLogs(ResponseBean responseBean) {
        Utils.print(TAG, "ResponseBean Success");
        Utils.print(TAG, "status = " + responseBean.getStatus() + "    msg = " + responseBean.getMessage());
        if (responseBean.getAuthToke() != null) {
            prefManager.setString(PrefConst.PREF_ACCESS_TOKEN, responseBean.getAuthToke().getAccessToken());
            Utils.print(TAG, "Generated Access Token   ::::  " +
                    responseBean.getAuthToke().getTokenType() + " " + prefManager.getString(PrefConst.PREF_ACCESS_TOKEN));
        }

        if (responseBean.getResult() != null && responseBean.getResult().getName() != null) {
            Result result = responseBean.getResult();
            Utils.print(TAG, "UserId = " + result.getId());
            Utils.print(TAG, "Name = " + result.getName());
            Utils.print(TAG, "Email = " + result.getEmail());
            Utils.print(TAG, "Country code = " + result.getCountryCode());
            Utils.print(TAG, "Phone No = " + result.getPhoneNumber());
            Utils.print(TAG, "Password = " + result.getPassword());
            Utils.print(TAG, "Profile picture = " + result.getAvatarUrl());
        } else if (responseBean.getResult() != null) {
            Result result = responseBean.getResult();
            Utils.print(TAG, "OTP = " + result.getOtp());
        } else {
            Utils.print(TAG, "Result = {}");
        }
    }
}
