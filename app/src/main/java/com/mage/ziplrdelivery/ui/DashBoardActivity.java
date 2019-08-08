package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.databinding.LayoutMapViewBinding;
import com.mage.ziplrdelivery.databinding.LayoutNavigationViewBinding;
import com.mage.ziplrdelivery.screen.Data;
import com.mage.ziplrdelivery.screen.Screen;
import com.mage.ziplrdelivery.model.data.Result;
import com.mage.ziplrdelivery.databinding.ActivityDashBoardBinding;
import com.mage.ziplrdelivery.screen.ScreenHelper;
import com.mage.ziplrdelivery.api.ApiConst;

import java.util.Objects;

public class DashBoardActivity extends BaseActivity implements ScreenHelper.DataMessageListener {

    private static final String TAG = Screen.DASH_BOARD_ACTIVITY;
    private ActivityDashBoardBinding binding;
    private LayoutMapViewBinding mapBinding;
    private LayoutNavigationViewBinding navBinding;

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
        mapBinding = binding.mapView;
        navBinding = binding.navigationView;
        return (S) binding;
    }

    @Override
    protected ScreenHelper.DataMessageListener addDataMessageListener() {
        return DashBoardActivity.this;
    }

    @Override
    protected void initUi() {
        binding.nonClickable.setOnClickListener(null);
        navBinding.ivNavClose.setOnClickListener(this);
        mapBinding.btLogout.setOnClickListener(this);
        Result data = utils.getLoginData(appManager);
        if (data != null) {
            mapBinding.tvTemp.setText("User_Id = " + data.getId() + "\nName = " + data.getName() + "\nEmail = " + data.getEmail() + "\nMobile No = "
                    + data.getCountryCode() + data.getPhoneNumber() + "\nImage_Url = " + data.getAvatarUrl());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btLogout:
                if (mapBinding.btLogout.isButtonEnabled()) {
                    callApi(1);
                }
            case R.id.ivNavClose:
                Objects.requireNonNull(screenHelper.getActivityList().get(Screen.DASH_BOARD_ACTIVITY)).finish();
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
                mapBinding.btLogout.showProgressBar(true, PROGRESS_TAG_0);
                apiController.getApiLogout();
                apiController.set0status(false);
            }
        } else {
            utils.showInternetMsg(mContext);
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
            utils.logoutFromApp(mContext);
        } else if (tag == ApiConst.LOGOUT && result == ApiConst.API_RESULT.FAIL) {
            enableScreen(true);
            mapBinding.btLogout.showProgressBar(false, PROGRESS_TAG_0);
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
