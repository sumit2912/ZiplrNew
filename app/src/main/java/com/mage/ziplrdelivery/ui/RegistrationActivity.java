package com.mage.ziplrdelivery.ui;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.databinding.ActivityRegistrationBinding;
import com.mage.ziplrdelivery.utils.Const;

public class RegistrationActivity extends BaseActivity implements AppManager.DataMessageListener {

    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RegistrationActivity.this, R.layout.activity_registration);
    }

    @Override
    protected Context getContext() {
        return RegistrationActivity.this;
    }

    @Override
    protected AppManager.DataMessageListener addDataMessageListener() {
        return RegistrationActivity.this;
    }

    @Override
    protected void initUi() {

    }

    @Override
    protected void onInternetChange(boolean isInternet) {

    }

    @Override
    protected void callApi(int tag) {

    }

    @Override
    public void onResponse(String tag, Const.API_RESULT result, int status, String msg) {

    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }
}
