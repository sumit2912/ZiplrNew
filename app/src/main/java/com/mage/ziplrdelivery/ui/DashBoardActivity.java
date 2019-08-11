package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.databinding.LayoutMapViewBinding;
import com.mage.ziplrdelivery.databinding.LayoutNavigationViewBinding;
import com.mage.ziplrdelivery.model.data.DashBoardBean;
import com.mage.ziplrdelivery.screen.Data;
import com.mage.ziplrdelivery.screen.Screen;
import com.mage.ziplrdelivery.model.data.Result;
import com.mage.ziplrdelivery.databinding.ActivityDashBoardBinding;
import com.mage.ziplrdelivery.screen.ScreenHelper;
import com.mage.ziplrdelivery.api.ApiConst;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.DashBoardViewModel;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.NavigationMenuViewModel;

import java.util.Objects;

public class DashBoardActivity extends BaseActivity implements ScreenHelper.DataMessageListener, Observer<DashBoardBean> {

    private static final String TAG = Screen.DASH_BOARD_ACTIVITY;
    private ActivityDashBoardBinding binding;
    private LayoutMapViewBinding mapBinding;
    private LayoutNavigationViewBinding navBinding;
    private DashBoardViewModel dashBoardViewModel;
    private NavigationMenuViewModel navigationMenuViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashBoardViewModel = viewModelFactory.create(DashBoardViewModel.class);
        navigationMenuViewModel = viewModelFactory.create(NavigationMenuViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setDashBoardViewModel(dashBoardViewModel);
        navBinding.setLifecycleOwner(this);
        navBinding.setNavigationMenuViewModel(navigationMenuViewModel);
        dashBoardViewModel.getDashBoardMutableLiveData().observe(this,this);
        navigationMenuViewModel.getNavigationMenuMutableLiveData().observe(this,this);
        initUi();
        singletonFactory.getLoginParamBean().resetAll();
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
        Result data = utils.getLoginData();
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

    @Override
    public void onChanged(DashBoardBean dashBoardBean) {
        Utils.print(TAG,"Profile Name = "+dashBoardBean.getProfileName());
        Utils.print(TAG,"Profile Email = "+dashBoardBean.getProfileEmail());
    }
}
