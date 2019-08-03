package com.mage.ziplrdelivery.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.textfield.TextInputEditText;
import com.mage.ziplrdelivery.api.ApiController;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.listener.ResponseListener;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.utils.constant.ApiConst;

import java.util.Objects;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, Utils.InternetCheck.NetListener {

    protected static final String KEY_FROM_ACTIVITY = "KEY_FROM_ACTIVITY";
    protected static final String KEY_FP_CLICK = "KEY_FP_CLICK";
    protected static final String KEY_BEAN_1 = "KEY_BEAN_1";
    protected static final String KEY_BEAN_2 = "KEY_BEAN_2";
    protected static final String KEY_BEAN_3 = "KEY_BEAN_3";
    protected final Handler handler = new Handler();
    protected String VALUE_FROM_ACTIVITY = null;
    protected boolean VALUE_FP_CLICK = false;
    protected boolean isInternet = false;
    protected AppManager appManager;
    protected Context mContext;
    protected boolean disableClick;
    protected Intent dataIntent;
    protected ApiController apiController;
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
        dataIntent = getIntent();
        apiController = new ApiController(mContext, this);
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

    protected abstract void enableScreen(boolean enable);

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onNetChange(boolean isInternet) {
        this.isInternet = isInternet;
        onInternetChange(isInternet);
    }

    protected String getEdValue(TextInputEditText ed) {
        return Objects.requireNonNull(ed.getText()).toString().trim();
    }

    protected String getResString(int resId) {
        return mContext.getResources().getString(resId);
    }

    @Override
    public void onResponse(String tag, ApiConst.API_RESULT result, int status, String msg) {
        Utils.print("BaseActivity", "tag = " + tag + " result = " + result + " status = " + status + " msg =" + msg);
    }
}
