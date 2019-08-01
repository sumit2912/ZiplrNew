package com.mage.ziplrdelivery.listener;

import retrofit2.Response;

public interface ApiResponseListener {
    void onSuccessOccur(String method, Response<?> response, boolean isResultListBean);
    void onErrorOccur(String method, Throwable e);
}
