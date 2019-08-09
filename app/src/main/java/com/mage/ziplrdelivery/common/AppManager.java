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
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private PrefManager prefManager;
    private ScreenHelper screenHelper;
    private Utils utils;
    private ApiResponseHelper apiResponseHelper;

    public AppManager(Context mContext) {
        this.context = mContext;
        sharedPreferences = context.getSharedPreferences(PrefConst.PREF_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        prefManager = new PrefManager(sharedPreferences, editor);
        screenHelper = new ScreenHelper();
        utils = new Utils();
        apiResponseHelper = new ApiResponseHelper();
    }

    public static AppManager getInstance(Context mContext) {
        if (appManager == null) {
            appManager = new AppManager(mContext);
        }
        return appManager;
    }

    public PrefManager getPrefManager() {
        return this.prefManager;
    }

    public ScreenHelper getScreenHelper() {
        return this.screenHelper;
    }

    public Utils getUtils() {
        return utils;
    }

    public ApiResponseHelper getApiResponseHelper() {
        return apiResponseHelper;
    }
}
