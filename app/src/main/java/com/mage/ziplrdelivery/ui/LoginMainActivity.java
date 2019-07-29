package com.mage.ziplrdelivery.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;

import com.mage.ziplrdelivery.databinding.ActivityLoginMainBinding;
import com.mage.ziplrdelivery.utils.Const;
import com.mage.ziplrdelivery.utils.Utils;


public class LoginMainActivity extends BaseActivity implements AppManager.DataMessageListener {

    private ActivityLoginMainBinding binding;
    private Intent registrationIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.makeFullScreenActivity(LoginMainActivity.this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginMainActivity.this, R.layout.activity_login_main);
        binding.btMobileNo.setOnClickListener(this);
        binding.tvRegistration.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return LoginMainActivity.this;
    }


    @Override
    protected AppManager.DataMessageListener addDataMessageListener() {
        return LoginMainActivity.this;
    }

    @Override
    protected void initUi() {

    }

    @Override
    protected void callApi(int tag) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btMobileNo:
                break;
            case R.id.tvRegistration:
                if(!disableClick){
                    disableClick = true;
                    if(registrationIntent == null){
                        registrationIntent = new Intent(LoginMainActivity.this,RegistrationActivity.class);
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
    public void onResponse(String tag, Const.API_RESULT result, int status, String msg) {

    }
}
