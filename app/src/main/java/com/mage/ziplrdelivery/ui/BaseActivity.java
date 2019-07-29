package com.mage.ziplrdelivery.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.listener.ResponseListener;
import com.mage.ziplrdelivery.utils.Utils;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, Utils.InternetCheck.NetListener {

    protected boolean isInternet = false;
    protected AppManager appManager;
    protected Context mContext;
    protected boolean disableClick;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            new Utils.InternetCheck(BaseActivity.this).execute();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        appManager = AppManager.getInstance(mContext);
        appManager.addActivity((AppCompatActivity) getContext());
        appManager.addDataMessageListener(getContext().getClass().getSimpleName(), addDataMessageListener());
        localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        localBroadcastManager.registerReceiver(broadcastReceiver, new IntentFilter("isInternet"));
        new Utils.InternetCheck(BaseActivity.this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        disableClick = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }

    protected abstract Context getContext();

    protected abstract AppManager.DataMessageListener addDataMessageListener();

    protected abstract void initUi();

    protected abstract void onInternetChange(boolean isInternet);

    protected abstract void callApi(int tag);

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onNetChange(boolean isInternet) {
        this.isInternet = isInternet;
        onInternetChange(isInternet);
    }
}
