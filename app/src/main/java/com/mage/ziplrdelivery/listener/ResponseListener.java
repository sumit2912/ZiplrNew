package com.mage.ziplrdelivery.listener;


import com.mage.ziplrdelivery.utils.constant.ApiConst;

public interface ResponseListener {
    // API Response Listener
    void onResponse(String tag, ApiConst.API_RESULT result, int status, String msg);
}
