package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.screen.Data;
import com.mage.ziplrdelivery.screen.Screen;
import com.mage.ziplrdelivery.model.data.Result;
import com.mage.ziplrdelivery.databinding.ActivityDashBoardBinding;
import com.mage.ziplrdelivery.model.param.LoginParamBean;
import com.mage.ziplrdelivery.screen.ScreenHelper;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.api.ApiConst;

public class DashBoardActivity extends BaseActivity implements ScreenHelper.DataMessageListener {

    private static final String TAG = Screen.DASH_BOARD_ACTIVITY;
    private ActivityDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singletonFactory.getLoginParamBean().resetAll();
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
    protected ScreenHelper.DataMessageListener addDataMessageListener() {
        return DashBoardActivity.this;
    }

    @Override
    protected void initUi() {
        binding.nonClickable.setOnClickListener(null);
        binding.btLogout.setOnClickListener(this);
        Result data = Utils.getLoginData(appManager);
        if (data != null) {
            binding.tvTemp.setText("User_Id = " + data.getId() + "\nName = " + data.getName() + "\nEmail = " + data.getEmail() + "\nMobile No = "
                    + data.getCountryCode() + data.getPhoneNumber() + "\nImage_Url = " + data.getAvatarUrl());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btLogout:
                if (binding.btLogout.isButtonEnabled()) {
                    callApi(1);
                }
                break;
        }
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
        if (isInternet) {
            if (tag == 1) {
                enableScreen(false);
                binding.btLogout.showProgressBar(true, PROGRESS_TAG_0);
                apiController.getApiLogout();
                apiController.set0status(false);
            }
        } else {
            Utils.showInternetMsg(mContext);
        }
    }

    @Override
    protected void enableScreen(boolean enable) {
        binding.nonClickable.setVisibility(enable ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onResponse(String tag, ApiConst.API_RESULT result, int status, String msg) {
        super.onResponse(tag,result,status,msg);
        if (tag == ApiConst.LOGOUT && result == ApiConst.API_RESULT.SUCCESS && status == 1) {
            Utils.logoutFromApp(mContext);
        } else if (tag == ApiConst.LOGOUT && result == ApiConst.API_RESULT.FAIL) {
            enableScreen(true);
            binding.btLogout.showProgressBar(false, PROGRESS_TAG_0);
        }
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
