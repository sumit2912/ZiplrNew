package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.common.Screen;
import com.mage.ziplrdelivery.databinding.ActivityRegistrationBinding;
import com.mage.ziplrdelivery.param_model.RegistrationParamBean;
import com.mage.ziplrdelivery.utils.constant.ApiConst;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.viewmodel.RegistrationViewModel;

import java.util.Objects;


public class RegistrationActivity extends BaseActivity implements AppManager.DataMessageListener, Observer<RegistrationParamBean> {

    private static final int PROGRESS_TAG = 0;
    private ActivityRegistrationBinding binding;
    private AppCompatImageView ivBack;
    private RegistrationViewModel registrationViewModel;
    private Intent verificationIntent;
    private RegistrationParamBean registrationParamBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RegistrationActivity.this, R.layout.activity_registration);
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setRegistrationViewModel(registrationViewModel);
        registrationViewModel.getRegistrationParamBean().observe(this, this);
        initUi();
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
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowCustomEnabled(true);
        View backView = LayoutInflater.from(this).inflate(R.layout.layout_back_arrow, binding.toolbar, false);
        ivBack = backView.findViewById(R.id.ivBack);
        getSupportActionBar().setCustomView(backView);
        binding.collapsingTl.setTitle(getResources().getString(R.string.registration));
        binding.collapsingTl.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarTitle);
        binding.collapsingTl.setExpandedTitleTextAppearance(R.style.ExpandedAppBarTitle);
        binding.collapsingTl.setCollapsedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        binding.collapsingTl.setExpandedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        ivBack.setOnClickListener(this);
        binding.ivFlag.setImageResource(R.drawable.img_flag);
        binding.tvCode.setText(mContext.getResources().getString(R.string.uk_country_code));
        registrationViewModel.CountryCode.setValue(mContext.getResources().getString(R.string.uk_country_code));
        binding.btSubmit.setOnClickListener(this);
        binding.btSubmit.setButtonClickListener(this);
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
        if(isInternet){
            if(tag == 1){
                if(registrationParamBean != null) {
                    binding.btSubmit.showProgressBar(true, PROGRESS_TAG);
                    apiController.getApiSignUp(registrationParamBean);
                }
            }
        }else {
            Utils.showInternetMsg(mContext);
        }
    }

    @Override
    public void onResponse(String tag, ApiConst.API_RESULT result, int status, String msg) {
        super.onResponse(tag,result,status,msg);
        Utils.print(Screen.REGISTRATION_ACTIVITY,"tag = "+tag+" result = "+result+" status = "+status+" msg ="+msg);
        if(tag == ApiConst.AUTH_SIGNUP && result == ApiConst.API_RESULT.SUCCESS && status == 1){
            if (verificationIntent == null)
                verificationIntent = new Intent(RegistrationActivity.this, VerificationActivity.class);
            VALUE_FROM_ACTIVITY = Screen.REGISTRATION_ACTIVITY;
            verificationIntent.putExtra(KEY_FROM_ACTIVITY, VALUE_FROM_ACTIVITY);
            verificationIntent.putExtra("otp",String.valueOf(apiController.getResultData().getOtp()));
            verificationIntent.putExtra("id",apiController.getResultData().getId());
            startActivity(verificationIntent);
            finish();
        }else if(tag == ApiConst.AUTH_SIGNUP && result == ApiConst.API_RESULT.FAIL){
            binding.btSubmit.showProgressBar(false, PROGRESS_TAG);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btSubmit:
                registrationViewModel.onClick(view);
                break;
        }
    }

    @Override
    public void onChanged(RegistrationParamBean registrationParamBean) {
        int error = registrationParamBean.isValidData();
        switch (error) {
            case 0:
                binding.edName.setError(getResources().getString(R.string.validation_name));
                binding.edName.requestFocus();
                break;
            case 1:
                binding.edEmail.setError(getResources().getString(R.string.validation_email));
                binding.edEmail.requestFocus();
                break;
            case 2:
                binding.edPassword.setError(getResources().getString(R.string.validation_password));
                binding.edPassword.requestFocus();
                break;
            case 3:
                binding.edCPassword.setError(getResources().getString(R.string.validation_c_password));
                binding.edCPassword.requestFocus();
                break;
            case 4:
                binding.edCPassword.setError(getResources().getString(R.string.validation_password_match));
                break;
            case 5:
                binding.tvCode.setError(getResources().getString(R.string.validation_country_code));
                break;
            case 6:
                binding.edPhoneNo.setError(getResources().getString(R.string.validation_phone_no));
                break;
            default:
                this.registrationParamBean = registrationParamBean;
                callApi(1);
                break;
        }

    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }
}
