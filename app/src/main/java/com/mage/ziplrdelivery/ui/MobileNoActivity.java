package com.mage.ziplrdelivery.ui;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.common.Screen;
import com.mage.ziplrdelivery.data_model.Result;
import com.mage.ziplrdelivery.databinding.ActivityMobileNoBinding;
import com.mage.ziplrdelivery.param_model.LoginParamBean;
import com.mage.ziplrdelivery.uc.CustomTextWatcher;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.utils.constant.ApiConst;
import com.mage.ziplrdelivery.viewmodel.LoginViewModel;
import com.mage.ziplrdelivery.viewmodel.LoginViewModelFactory;

public class MobileNoActivity extends BaseActivity implements AppManager.DataMessageListener, Observer<LoginParamBean> {

    private static final String TAG = Screen.MOBILE_NO_ACTIVITY;
    private ActivityMobileNoBinding binding;
    private AppCompatImageView ivBack;
    private Intent passwordIntent, registerIntent, verifyIntent;
    private String phoneNumber;
    private LoginViewModelFactory viewModelFactory;
    private LoginViewModel loginViewModel;
    private LoginParamBean loginParamBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelFactory = LoginViewModelFactory.provideViewModelFactory();
        viewModelFactory.clearLoginViewModel();
        loginViewModel = viewModelFactory.create(LoginViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);
        loginViewModel.getLoginParamBean().observe(this, this);
        initUi();
    }

    @Override
    protected Context getContext() {
        return MobileNoActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mobile_no;
    }

    @Override
    protected <S> S getViewBinding(S s) {
        binding = (ActivityMobileNoBinding) s;
        return (S) binding;
    }

    @Override
    protected AppManager.DataMessageListener addDataMessageListener() {
        return MobileNoActivity.this;
    }

    @Override
    protected void initUi() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View backView = LayoutInflater.from(this).inflate(R.layout.layout_back_arrow, binding.toolbar, false);
        ivBack = backView.findViewById(R.id.ivBack);
        getSupportActionBar().setCustomView(backView);
        binding.collapsingTl.setTitle(getResources().getString(R.string.enter_your_mobile_number));
        binding.collapsingTl.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarTitle);
        binding.collapsingTl.setExpandedTitleTextAppearance(R.style.ExpandedAppBarTitle);
        binding.collapsingTl.setCollapsedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        binding.collapsingTl.setExpandedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        ivBack.setOnClickListener(this);
        binding.ivFlag.setImageResource(R.drawable.img_flag);
        binding.tvCode.setText(getResources().getString(R.string.uk_country_code));
        binding.btNext.setOnClickListener(this);
        binding.nonClickable.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btNext:
                if (binding.btNext.isButtonEnabled()) {
                    loginViewModel.onClick(view);
                }
                break;
        }
    }

    private void validation() {
        int error = LoginParamBean.getInstance().isValidPhoneNumber();
        switch (error) {
            case 0:
                binding.edMobileNo.setError(getResString(R.string.validation_mobile_no));
                binding.edMobileNo.requestFocus();
                break;
            case 1:
                binding.edMobileNo.setError(getResString(R.string.validation_mobile_no_length).replace("0", "10"));
                binding.edMobileNo.requestFocus();
                break;
            default:
                this.loginParamBean = loginParamBean;
                callApi(1);
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
                Utils.hideKeyBoardFromView(mContext);
                binding.btNext.showProgressBar(true, PROGRESS_TAG_0);
                enableScreen(false);
                apiController.getApiPhoneCheck(loginParamBean);
                apiController.set0status(false);
            } else if (tag == 2) {
                super.showProgressBar(true);
                apiController.getApiSendOTP(phoneNumber);
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
        Utils.print(TAG, "tag = " + tag + " result = " + result + " status = " + status + " msg = " + msg);
        if (tag == ApiConst.PHONE_CHECK && result == ApiConst.API_RESULT.SUCCESS && status == 1) {
            binding.btNext.showProgressBar(false, PROGRESS_TAG_0);
            enableScreen(true);
            if (passwordIntent == null)
                passwordIntent = new Intent(MobileNoActivity.this, PasswordActivity.class);
            startActivity(passwordIntent);
        } else if (tag == ApiConst.PHONE_CHECK && result == ApiConst.API_RESULT.FAIL) {
            binding.btNext.showProgressBar(false, PROGRESS_TAG_0);
            enableScreen(true);
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
            Utils.toast(mContext, msg, false);
            enableScreen(true);
            super.showProgressBar(false);
            verifyIntent = new Intent(MobileNoActivity.this, VerificationActivity.class);
            Bundle bundle = new Bundle();
            Result data = new Result();
            data.setPhoneNumber(loginParamBean.getPhone_number());
            bundle.putSerializable(KEY_RESULT_BEAN, data);
            verifyIntent.putExtras(bundle);
            verifyIntent.putExtra(KEY_FROM_ACTIVITY, TAG);
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
                registerIntent = new Intent(MobileNoActivity.this, RegistrationActivity.class);
                startActivity(registerIntent);
                break;
            case TYPE_NOT_VERIFIED:
                callApi(2);
                break;
        }
    }

    @Override
    public void onChanged(LoginParamBean loginParamBean) {
        int error = loginParamBean.isValidPhoneNumber();
        switch (error) {
            case 0:
                binding.edMobileNo.setError(getResString(R.string.validation_mobile_no));
                binding.edMobileNo.requestFocus();
                break;
            case 1:
                binding.edMobileNo.setError(getResString(R.string.validation_mobile_no_length).replace("0", "10"));
                binding.edMobileNo.requestFocus();
                break;
            default:
                this.loginParamBean = loginParamBean;
                callApi(1);
                break;

        }
    }
}
