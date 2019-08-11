package com.mage.ziplrdelivery.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.mage.ziplrdelivery.api.ApiResponseHelper;
import com.mage.ziplrdelivery.prefmanager.PrefConst;
import com.mage.ziplrdelivery.prefmanager.PrefManager;
import com.mage.ziplrdelivery.screen.ScreenHelper;
import com.mage.ziplrdelivery.utils.Utils;


public class AppManager {
    private static AppManager appManager;
    private Context context;
    private PrefManager prefManager;
    private ScreenHelper screenHelper;
    private Utils utils;
    private ApiResponseHelper apiResponseHelper;

    public AppManager(Context mContext) {
        this.context = mContext;
    }

    public static AppManager getInstance(Context mContext) {
        if (appManager == null) {
            appManager = new AppManager(mContext);
        }
        return appManager;
    }

    public Context getApplicationContext() {
        return this.context;
    }

    public void setPrefManager(PrefManager prefManager) {
        this.prefManager = prefManager;
    }

    public PrefManager getPrefManager() {
        return this.prefManager;
    }

    public void setScreenHelper(ScreenHelper screenHelper) {
        this.screenHelper = screenHelper;
    }

    public ScreenHelper getScreenHelper() {
        return this.screenHelper;
    }

    public void setUtils(Utils utils) {
        this.utils = utils;
    }

    public Utils getUtils() {
        return utils;
    }

    public void setApiResponseHelper(ApiResponseHelper apiResponseHelper) {
        this.apiResponseHelper = apiResponseHelper;
    }

    public ApiResponseHelper getApiResponseHelper() {
        return apiResponseHelper;
    }
}
