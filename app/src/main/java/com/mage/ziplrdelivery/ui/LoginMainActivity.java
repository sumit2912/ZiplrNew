package com.mage.ziplrdelivery.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;

import com.mage.ziplrdelivery.common.Screen;
import com.mage.ziplrdelivery.databinding.ActivityLoginMainBinding;
import com.mage.ziplrdelivery.utils.constant.ApiConst;
import com.mage.ziplrdelivery.utils.Utils;


public class LoginMainActivity extends BaseActivity implements AppManager.DataMessageListener {

    private static final String TAG = Screen.LOGIN_MAIN_ACTIVITY;
    private ActivityLoginMainBinding binding;
    private Intent registrationIntent, mobileNoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.makeFullScreenActivity(LoginMainActivity.this);
        super.onCreate(savedInstanceState);
        initUi();
    }

    @Override
    protected Context getContext() {
        return LoginMainActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_main;
    }

    @Override
    protected <S> S getViewBinding(S s) {
        binding = (ActivityLoginMainBinding) s;
        return (S) binding;
    }


    @Override
    protected AppManager.DataMessageListener addDataMessageListener() {
        return LoginMainActivity.this;
    }

    @Override
    protected void initUi() {
        binding.btMobileNo.setOnClickListener(this);
        binding.tvRegistration.setOnClickListener(this);
        binding.nonClickable.setOnClickListener(null);
    }

    @Override
    protected void callApi(int tag) {

    }

    @Override
    protected void enableScreen(boolean enable) {
        binding.nonClickable.setVisibility(enable ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btMobileNo:
                if (!disableClick) {
                    disableClick = true;
                    if (mobileNoIntent == null) {
                        mobileNoIntent = new Intent(LoginMainActivity.this, MobileNoActivity.class);
                    }
                    startActivity(mobileNoIntent);
                }
                break;
            case R.id.tvRegistration:
                if (!disableClick) {
                    disableClick = true;
                    if (registrationIntent == null) {
                        registrationIntent = new Intent(LoginMainActivity.this, RegistrationActivity.class);
                    }
                    startActivity(registrationIntent);
                }
                break;
        }
    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

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

    @Override
    public void onNegativeClicked(String type) {

    }

    @Override
    public void onPositiveClicked(String type) {

    }
}
