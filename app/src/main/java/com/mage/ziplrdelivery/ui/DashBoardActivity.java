package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.common.Screen;
import com.mage.ziplrdelivery.data_model.Result;
import com.mage.ziplrdelivery.databinding.ActivityDashBoardBinding;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.utils.constant.ApiConst;

public class DashBoardActivity extends BaseActivity implements AppManager.DataMessageListener {
    private static final String TAG = Screen.DASH_BOARD_ACTIVITY;
    private ActivityDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    @Override
    protected Context getContext() {
        return DashBoardActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dash_board;
    }

    @Override
    protected <S> S getViewBinding(S s) {
        binding = (ActivityDashBoardBinding) s;
        return (S) binding;
    }

    @Override
    protected AppManager.DataMessageListener addDataMessageListener() {
        return DashBoardActivity.this;
    }

    @Override
    protected void initUi() {
        binding.nonClickable.setOnClickListener(null);
        Result data = Utils.getLoginData(appManager);
        if (data != null) {
            binding.tvTemp.setText("User_Id = " + data.getId() + "\nName = " + data.getName() + "\nEmail = " + data.getEmail() + "\nMobile No = "
                    + data.getCountryCode() + data.getPhoneNumber() + "\nImage_Url = " + data.getAvatarUrl());
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onInternetChange(boolean isInternet) {
        if (isInternet) {
            binding.rlInternet.setVisibility(View.GONE);
        } else {
            binding.rlInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void callApi(int tag) {

    }

    @Override
    protected void enableScreen(boolean enable) {
        binding.nonClickable.setVisibility(enable ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onResponse(String tag, ApiConst.API_RESULT result, int status, String msg) {

    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }

    @Override
    public void onNegativeClicked(String type) {

    }

    @Override
    public void onPositiveClicked(String type) {

    }
}
