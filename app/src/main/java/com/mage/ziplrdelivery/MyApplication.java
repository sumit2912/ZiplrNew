package com.mage.ziplrdelivery;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Callbacks;
import com.mage.ziplrdelivery.receiver.NetworkChangeReceiver;

public class MyApplication extends Application {

    private NetworkChangeReceiver networkChangeReceiver;
    private Callbacks callbacks;
    private AppManager appManager;

    @Override
    public void onCreate() {
        super.onCreate();
        appManager = AppManager.getInstance(getApplicationContext());
        callbacks = new Callbacks();
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerActivityLifecycleCallbacks(callbacks);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        appManager.clearAllList();
        unregisterReceiver(networkChangeReceiver);
    }
}
