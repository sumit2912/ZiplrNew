package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.databinding.ActivitySplashBinding;
import com.mage.ziplrdelivery.utils.constant.ApiConst;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.utils.constant.PrefConst;

public class SplashActivity extends BaseActivity implements AppManager.DataMessageListener {

    Runnable runnable;
    private ActivitySplashBinding binding;
    private Intent goIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.makeFullScreenActivity(SplashActivity.this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SplashActivity.this, R.layout.activity_splash);
        runnable = () -> {
            if(appManager.prefGetStringValue(PrefConst.PREF_ACCESS_TOKEN).isEmpty()){
                goIntent = new Intent(SplashActivity.this, LoginMainActivity.class);
            }else {
                goIntent = new Intent(SplashActivity.this, DashBoardActivity.class);
            }
            startActivity(goIntent);
            finish();
        };

        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected Context getContext() {
        return SplashActivity.this;
    }

    @Override
    protected AppManager.DataMessageListener addDataMessageListener() {
        return SplashActivity.this;
    }

    @Override
    protected void initUi() {
    }

    @Override
    protected void callApi(int tag) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onInternetChange(boolean isInternet) {
        if (isInternet) {
            binding.rlInternet.setVisibility(View.GONE);
        } else {
            binding.rlInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResponse(String tag, ApiConst.API_RESULT result, int status, String msg) {

    }
}
