package com.mage.ziplrdelivery.listener;

import com.mage.ziplrdelivery.data_model.ResponseBean;

import retrofit2.Response;

public interface ApiResponseListener {
    void onSuccessOccur(String method, Response<ResponseBean> response);
    void onErrorOccur(String method, Throwable e);
}
