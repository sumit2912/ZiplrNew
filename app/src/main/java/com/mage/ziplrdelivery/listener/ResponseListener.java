package com.mage.ziplrdelivery.listener;


import com.mage.ziplrdelivery.utils.Const;

public interface ResponseListener {
    // API Response Listener
    void onResponse(String tag, Const.API_RESULT result, int status, String msg);
}
