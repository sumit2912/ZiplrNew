package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.databinding.ActivitySplashBinding;
import com.mage.ziplrdelivery.utils.Const;
import com.mage.ziplrdelivery.utils.Utils;

public class SplashActivity extends BaseActivity implements AppManager.DataMessageListener {

    final Handler handler = new Handler();
    Runnable runnable;
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.makeFullScreenActivity(SplashActivity.this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SplashActivity.this, R.layout.activity_splash);
        runnable = () -> {
            startActivity(new Intent(SplashActivity.this, LoginMainActivity.class));
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
    public void onResponse(String tag, Const.API_RESULT result, int status, String msg) {

    }
}
