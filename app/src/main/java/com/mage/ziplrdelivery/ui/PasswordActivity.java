package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.screen.Data;
import com.mage.ziplrdelivery.screen.Screen;
import com.mage.ziplrdelivery.model.data.Result;
import com.mage.ziplrdelivery.databinding.ActivityPasswordBinding;
import com.mage.ziplrdelivery.model.param.LoginParamBean;
import com.mage.ziplrdelivery.screen.ScreenHelper;
import com.mage.ziplrdelivery.api.ApiConst;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.viewmodelfactory.ViewModelIdentifier;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.PasswordViewModel;

import java.util.Objects;

public class PasswordActivity extends BaseActivity implements ScreenHelper.DataMessageListener, Observer<LoginParamBean> {

    private static final String TAG = Screen.PASSWORD_ACTIVITY;
    private ActivityPasswordBinding binding;
    private AppCompatImageView ivBack;
    private Intent verifyIntent, dashBoardIntent, registerIntent;
    private PasswordViewModel passwordViewModel;
    private LoginParamBean loginParamBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginParamBean = singletonFactory.getLoginParamBean();
        loginParamBean.setPassword(null);
        singletonFactory.setLoginParamBean(loginParamBean);
        screenHelper.getViewModelList().remove(ViewModelIdentifier.KEY_PASSWORD_VIEW_MODEL);
        screenHelper.getViewModelDestroyerList().remove(ViewModelIdentifier.DES_PASSWORD_VIEW_MODEL);
        passwordViewModel = viewModelFactory.create(PasswordViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setPasswordViewModel(passwordViewModel);
        passwordViewModel.getPasswordLiveData().observe(this, this);
        utils.print(TAG,"PhNO = "+loginParamBean.getPhone_number());
        initUi();
    }

    @Override
    protected Context getContext() {
        return PasswordActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password;
    }

    @Override
    protected <S> S getViewBinding(S s) {
        binding = (ActivityPasswordBinding) s;
        return (S) binding;
    }

    @Override
    protected ScreenHelper.DataMessageListener addDataMessageListener() {
        return PasswordActivity.this;
    }

    @Override
    protected void initUi() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View backView = LayoutInflater.from(this).inflate(R.layout.layout_back_arrow, binding.toolbar, false);
        ivBack = backView.findViewById(R.id.ivBack);
        getSupportActionBar().setCustomView(backView);
        binding.collapsingTl.setTitle(getResources().getString(R.string.enter_your_password));
        binding.collapsingTl.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarTitle);
        binding.collapsingTl.setExpandedTitleTextAppearance(R.style.ExpandedAppBarTitle);
        binding.collapsingTl.setCollapsedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        binding.collapsingTl.setExpandedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        ivBack.setOnClickListener(this);
        binding.tvForgot.setPaintFlags(binding.tvForgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.tvForgot.setOnClickListener(this);
        binding.btSubmit.setOnClickListener(this);
        binding.nonClickable.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.tvForgot:
                if (!disableClick) {
                    disableClick = true;
                    callApi(2);
                }
                break;
            case R.id.btSubmit:
                if (binding.btSubmit.isButtonEnabled()) {
                    passwordViewModel.onClick(view);
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
                utils.hideKeyBoardFromView(mContext);
                enableScreen(false);
                binding.btSubmit.showProgressBar(true, PROGRESS_TAG_0);
                singletonFactory.setLoginParamBean(loginParamBean);
                apiController.getApiLogin();
            } else if (tag == 2) {
                enableScreen(false);
                showProgressBar(true);
                apiController.getApiSendOTP(loginParamBean.getPhone_number());
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
        if (tag == ApiConst.LOGIN && result == ApiConst.API_RESULT.SUCCESS && status == 1) {
            Result data = appManager.getApiResponseHelper().getResponseBean().getResult();
            if (data != null) {
                singletonFactory.getDashBoardBean().setProfileName(data.getName());
                singletonFactory.getDashBoardBean().setProfileEmail(data.getEmail());
                utils.storeLoginData(data);
            }
            if (dashBoardIntent == null)
                dashBoardIntent = new Intent(PasswordActivity.this, DashBoardActivity.class);
            startActivity(dashBoardIntent);
            finishAffinity();
        } else if (tag == ApiConst.LOGIN && result == ApiConst.API_RESULT.FAIL) {
            enableScreen(true);
            binding.btSubmit.showProgressBar(false, PROGRESS_TAG_0);

            if (status == 4) { // not registered
                adManager.init(TYPE_NOT_REGISTERED, getResString(R.string.mobile_number), super.getResString(R.string.alert_msg_not_registered));
                adManager.setNegativePositive("", getResString(R.string.lbl_register));
                adManager.show();
            } else if (status == 3) { // not verified
                adManager.init(TYPE_NOT_VERIFIED, getResString(R.string.mobile_number), super.getResString(R.string.alert_msg_not_verified));
                adManager.setNegativePositive("", getResString(R.string.lbl_verify));
                adManager.show();
            }
        }

        if (tag == ApiConst.SEND_OTP && result == ApiConst.API_RESULT.SUCCESS && status == 1) {
            utils.toast(mContext, msg, false);
            enableScreen(true);
            super.showProgressBar(false);
            verifyIntent = new Intent(PasswordActivity.this, VerificationActivity.class);
            Bundle bundle = new Bundle();
            Result data = new Result();
            data.setPhoneNumber(loginParamBean.getPhone_number());
            data.setPassword(loginParamBean.getPassword());
            bundle.putSerializable(KEY_RESULT_BEAN, data);
            verifyIntent.putExtras(bundle);
            verifyIntent.putExtra(KEY_FROM_ACTIVITY, TAG);
            verifyIntent.putExtra(KEY_FP_CLICK, true);
            startActivity(verifyIntent);
        } else if (tag == ApiConst.SEND_OTP && result == ApiConst.API_RESULT.FAIL) {
            enableScreen(true);
            super.showProgressBar(false);
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
        switch (type) {
            case TYPE_NOT_REGISTERED:
                finish();
                registerIntent = new Intent(PasswordActivity.this, RegistrationActivity.class);
                startActivity(registerIntent);
                if (screenHelper.getActivityList().containsKey(Screen.MOBILE_NO_ACTIVITY)) {
                    Objects.requireNonNull(screenHelper.getActivityList().get(Screen.MOBILE_NO_ACTIVITY)).finish();
                }
                finish();
                break;
            case TYPE_NOT_VERIFIED:
                callApi(2);
                break;
        }
    }

    @Override
    public void onChanged(LoginParamBean loginParamBean) {
        int error = loginParamBean.isValidPassword();
        if (error == 0) {
            binding.edPassword.setError(getResString(R.string.validation_password));
            binding.edPassword.requestFocus();
        } else {
            singletonFactory.setLoginParamBean(loginParamBean);
            this.loginParamBean = singletonFactory.getLoginParamBean();
            callApi(1);
        }
    }
}
