package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.screen.Data;
import com.mage.ziplrdelivery.screen.Screen;
import com.mage.ziplrdelivery.databinding.ActivityRegistrationBinding;
import com.mage.ziplrdelivery.model.param.RegistrationParamBean;
import com.mage.ziplrdelivery.api.ApiConst;
import com.mage.ziplrdelivery.screen.ScreenHelper;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.RegistrationViewModel;

import java.util.Objects;


public class RegistrationActivity extends BaseActivity implements ScreenHelper.DataMessageListener, Observer<RegistrationParamBean> {

    private static final String TAG = Screen.REGISTRATION_ACTIVITY;
    private ActivityRegistrationBinding binding;
    private AppCompatImageView ivBack;
    private RegistrationViewModel registrationViewModel;
    private Intent verificationIntent;
    private RegistrationParamBean registrationParamBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationViewModel = viewModelFactory.create(RegistrationViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setRegistrationViewModel(registrationViewModel);
        registrationViewModel.getRegistrationLiveData().observe(this, this);
        initUi();
    }

    @Override
    protected Context getContext() {
        return RegistrationActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    protected <S> S getViewBinding(S s) {
        binding = (ActivityRegistrationBinding) s;
        return (S) binding;
    }

    @Override
    protected ScreenHelper.DataMessageListener addDataMessageListener() {
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
        binding.nonClickable.setOnClickListener(null);
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
                if (registrationParamBean != null) {
                    binding.btSubmit.showProgressBar(true, PROGRESS_TAG_0);
                    enableScreen(false);
                    apiController.getApiSignUp(registrationParamBean);
                }
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
        if (tag == ApiConst.SIGN_UP && result == ApiConst.API_RESULT.SUCCESS && status == 1) {
            utils.toast(mContext, msg, false);
            if (verificationIntent == null)
                verificationIntent = new Intent(RegistrationActivity.this, VerificationActivity.class);
            verificationIntent.putExtra(KEY_FROM_ACTIVITY, TAG);
            Bundle bundle = new Bundle();
            apiController.getResultData().setPassword(registrationParamBean.getPassword());
            bundle.putSerializable(KEY_RESULT_BEAN, apiController.getResultData());
            verificationIntent.putExtras(bundle);
            startActivity(verificationIntent);
            finish();
        } else if (tag == ApiConst.SIGN_UP && result == ApiConst.API_RESULT.FAIL) {
            binding.btSubmit.showProgressBar(false, PROGRESS_TAG_0);
            enableScreen(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btSubmit:
                if (binding.btSubmit.isButtonEnabled()) {
                    registrationViewModel.onClick(view);
                }
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
                binding.edCPassword.requestFocus();
                break;
            case 5:
                binding.tvCode.setError(getResources().getString(R.string.validation_country_code));
                binding.tvCode.requestFocus();
                break;
            case 6:
                binding.edPhoneNo.setError(getResources().getString(R.string.validation_phone_no));
                binding.edPhoneNo.requestFocus();
                break;
            case 7:
                binding.edPhoneNo.setError(getResources().getString(R.string.validation_phone_no_length).replace("0", "10"));
                binding.edPhoneNo.requestFocus();
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

    @Override
    public void onNegativeClicked(String type) {

    }

    @Override
    public void onPositiveClicked(String type) {

    }
}
