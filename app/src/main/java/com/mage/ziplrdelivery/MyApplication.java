package com.mage.ziplrdelivery;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.mage.ziplrdelivery.api.ApiResponseHelper;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Callbacks;
import com.mage.ziplrdelivery.model.SingletonFactory;
import com.mage.ziplrdelivery.prefmanager.PrefConst;
import com.mage.ziplrdelivery.prefmanager.PrefManager;
import com.mage.ziplrdelivery.receiver.NetworkChangeReceiver;
import com.mage.ziplrdelivery.screen.ScreenHelper;
import com.mage.ziplrdelivery.utils.Utils;

public class MyApplication extends Application {

    private static AppManager appManager;
    private static Context context;
    private NetworkChangeReceiver networkChangeReceiver;
    private Callbacks callbacks;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private PrefManager prefManager;
    private ScreenHelper screenHelper;
    private Utils utils;
    private ApiResponseHelper apiResponseHelper;

    public static AppManager getAppManager() {
        return appManager;
    }

    public static void setAppManager(AppManager am) {
        appManager = am;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        callbacks = new Callbacks();
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerActivityLifecycleCallbacks(callbacks);
        initBasic();
    }

    private void initBasic() {
        if (appManager == null) {
            appManager = AppManager.getInstance(getApplicationContext());
        }
        if (sharedPreferences == null) {
            sharedPreferences = getApplicationContext().getSharedPreferences(PrefConst.PREF_FILE, MODE_PRIVATE);
        }
        if(editor == null){
            editor = sharedPreferences.edit();
        }
        if(prefManager == null){
            prefManager = new PrefManager(sharedPreferences,editor);
        }
        appManager.setPrefManager(prefManager);
        if(screenHelper == null){
            screenHelper = new ScreenHelper();
        }
        appManager.setScreenHelper(screenHelper);
        if(utils == null){
            utils = new Utils(appManager,prefManager);
        }
        appManager.setUtils(utils);
        if(apiResponseHelper == null){
            apiResponseHelper = new ApiResponseHelper(prefManager);
        }
        appManager.setApiResponseHelper(apiResponseHelper);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        appManager.getPrefManager().clearAll();
        SingletonFactory.getInstance().setSingletonFactory(null);
        unregisterReceiver(networkChangeReceiver);
    }
}
