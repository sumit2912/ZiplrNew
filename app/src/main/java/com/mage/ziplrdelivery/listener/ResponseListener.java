package com.mage.ziplrdelivery.listener;


import com.mage.ziplrdelivery.utils.constant.ComConst;

public interface ResponseListener {
    // API Response Listener
    void onResponse(String tag, ComConst.API_RESULT result, int status, String msg);
}
