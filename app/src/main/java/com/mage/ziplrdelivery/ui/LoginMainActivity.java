package com.mage.ziplrdelivery.ui;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.uc.CustomTextView;

import com.mage.ziplrdelivery.utils.Const;
import com.mage.ziplrdelivery.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginMainActivity extends BaseActivity implements AppManager.DataMessageListener {

    @BindView(R.id.tvInternet)
    CustomTextView tvInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.makeFullScreenActivity(LoginMainActivity.this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Context getContext() {
        return LoginMainActivity.this;
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_login_main;
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

    @OnClick({})
    public void onClick(View view) {

    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }

    @Override
    public void onInternetChange(boolean isInternet) {
        if (isInternet) {
                tvInternet.setVisibility(View.GONE);
        } else {
                tvInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResponse(String tag, Const.API_RESULT result, int status, String msg) {

    }
}
