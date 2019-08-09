package com.mage.ziplrdelivery;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Callbacks;
import com.mage.ziplrdelivery.receiver.NetworkChangeReceiver;

public class MyApplication extends Application {

    private static AppManager appManager;
    private static Context context;
    private NetworkChangeReceiver networkChangeReceiver;
    private Callbacks callbacks;

    public static Context getAppContext() {
        return context;
    }

    public static AppManager getAppManager() {
        return appManager;
    }

    public static void setAppManager(AppManager am) {
        appManager = am;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (context == null) {
            context = getApplicationContext();
        }

        if (appManager == null) {
            appManager = AppManager.getInstance(getApplicationContext());
        }

        callbacks = new Callbacks();
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerActivityLifecycleCallbacks(callbacks);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        appManager.getPrefManager().clearAll();
        unregisterReceiver(networkChangeReceiver);
    }
}
