package com.mage.ziplrdelivery.ui;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;

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
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.utils.constant.ApiConst;
import com.mage.ziplrdelivery.viewmodel.LoginViewModelFactory;
import com.mage.ziplrdelivery.viewmodel.MobileNoViewModel;

public class MobileNoActivity extends BaseActivity implements AppManager.DataMessageListener, Observer<LoginParamBean> {

    private static final String TAG = Screen.MOBILE_NO_ACTIVITY;
    private ActivityMobileNoBinding binding;
    private AppCompatImageView ivBack;
    private Intent passwordIntent, registerIntent, verifyIntent;
    private LoginViewModelFactory viewModelFactory;
    private MobileNoViewModel mobileNoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appManager.getLoginViewModelList().clear();
        viewModelFactory = new LoginViewModelFactory(appManager.getLoginViewModelList());
        mobileNoViewModel = viewModelFactory.create(MobileNoViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setMobileNoViewModel(mobileNoViewModel);
        mobileNoViewModel.getMobileNoLiveData().observe(this, this);
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
                    mobileNoViewModel.onClick(view);
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
            LoginParamBean.getInstance().setPassword(null);
            if (tag == 1) {
                Utils.hideKeyBoardFromView(mContext);
                binding.btNext.showProgressBar(true, PROGRESS_TAG_0);
                enableScreen(false);
                apiController.getApiPhoneCheck(LoginParamBean.getInstance());
                apiController.set0status(false);
            } else if (tag == 2) {
                super.showProgressBar(true);
                apiController.getApiSendOTP(LoginParamBean.getInstance().getPhone_number());
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
        if (tag == ApiConst.PHONE_CHECK && result == ApiConst.API_RESULT.SUCCESS && status == 1) {
            binding.btNext.showProgressBar(false, PROGRESS_TAG_0);
            enableScreen(true);
            if (passwordIntent == null)
                passwordIntent = new Intent(MobileNoActivity.this, PasswordActivity.class);
                passwordIntent.putExtra(KEY_PHONE_NO,LoginParamBean.getInstance().getPhone_number());
                Utils.print(TAG,"KEY_PHONE_NO = "+LoginParamBean.getInstance().getPhone_number());
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
            data.setPhoneNumber(LoginParamBean.getInstance().getPhone_number());
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
                callApi(1);
                break;

        }
    }
}
