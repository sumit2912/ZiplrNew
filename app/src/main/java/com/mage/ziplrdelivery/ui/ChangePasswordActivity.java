package com.mage.ziplrdelivery.ui;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.screen.Data;
import com.mage.ziplrdelivery.screen.Screen;
import com.mage.ziplrdelivery.databinding.ActivityChangePasswordBinding;
import com.mage.ziplrdelivery.model.param.LoginParamBean;
import com.mage.ziplrdelivery.screen.ScreenHelper;
import com.mage.ziplrdelivery.uc.CustomTextWatcher;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.api.ApiConst;

public class ChangePasswordActivity extends BaseActivity implements ScreenHelper.DataMessageListener, CustomTextWatcher.TextWatcherListener {

    private static final String TAG = Screen.CHANGE_PASSWORD_ACTIVITY;
    private ActivityChangePasswordBinding binding;
    private AppCompatImageView ivBack;
    private LoginParamBean loginParamBean;
    private String newPassword = "", confirmPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginParamBean = singletonFactory.getLoginParamBean();
        initUi();
    }

    @Override
    protected Context getContext() {
        return ChangePasswordActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected <S> S getViewBinding(S s) {
        binding = (ActivityChangePasswordBinding) s;
        return (S) binding;
    }

    @Override
    protected ScreenHelper.DataMessageListener addDataMessageListener() {
        return ChangePasswordActivity.this;
    }

    @Override
    protected void initUi() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View backView = LayoutInflater.from(this).inflate(R.layout.layout_back_arrow, binding.toolbar, false);
        ivBack = backView.findViewById(R.id.ivBack);
        getSupportActionBar().setCustomView(backView);
        binding.collapsingTl.setTitle(getResources().getString(R.string.change_password));
        binding.collapsingTl.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarTitle);
        binding.collapsingTl.setExpandedTitleTextAppearance(R.style.ExpandedAppBarTitle);
        binding.collapsingTl.setCollapsedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        binding.collapsingTl.setExpandedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        binding.nonClickable.setOnClickListener(null);
        ivBack.setOnClickListener(this);
        binding.btSubmit.setOnClickListener(this);
        binding.edNewPassword.addTextChangedListener(new CustomTextWatcher(0, ChangePasswordActivity.this));
        binding.edConfPassword.addTextChangedListener(new CustomTextWatcher(1, ChangePasswordActivity.this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSubmit:
                if (binding.btSubmit.isButtonEnabled()) {
                    validation();
                }
                break;
        }
    }

    private void validation() {
        if (TextUtils.isEmpty(newPassword)) {
            binding.edNewPassword.setError(getResString(R.string.validation_new_password));
            binding.edNewPassword.requestFocus();
        } else if (TextUtils.isEmpty(confirmPassword)) {
            binding.edConfPassword.setError(getResString(R.string.validation_c_password));
            binding.edConfPassword.requestFocus();
        } else if (!newPassword.equals(confirmPassword)) {
            binding.edConfPassword.setError(getResString(R.string.validation_new_password_match));
            binding.edConfPassword.requestFocus();
        } else {
            callApi(1);
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
                binding.btSubmit.showProgressBar(true, PROGRESS_TAG_0);
                enableScreen(false);
                apiController.getApiForgotPassword(loginParamBean);
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
        if (tag == ApiConst.FORGOT_PASSWORD && result == ApiConst.API_RESULT.SUCCESS && status == 1) {
            utils.toast(mContext,msg,false);
            finish();
        } else if (tag == ApiConst.FORGOT_PASSWORD && result == ApiConst.API_RESULT.FAIL) {
            binding.btSubmit.showProgressBar(false, PROGRESS_TAG_0);
            enableScreen(true);
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
    public void onTextChanged(int edTag, String text) {
        switch (edTag) {
            case 0:
                newPassword = text;
                loginParamBean.setPassword(newPassword);
                break;
            case 1:
                confirmPassword = text;
                break;
        }
    }
}
