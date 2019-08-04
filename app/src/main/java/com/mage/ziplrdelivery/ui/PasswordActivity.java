package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.common.Screen;
import com.mage.ziplrdelivery.databinding.ActivityPasswordBinding;
import com.mage.ziplrdelivery.param_model.LoginParamBean;
import com.mage.ziplrdelivery.uc.CustomTextWatcher;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.utils.constant.ApiConst;

public class PasswordActivity extends BaseActivity implements AppManager.DataMessageListener, CustomTextWatcher.TextWatcherListener {

    private static final String TAG = Screen.PASSWORD_ACTIVITY;
    private ActivityPasswordBinding binding;
    private AppCompatImageView ivBack;
    private LoginParamBean loginParamBean;
    private Intent verifyIntent, dashBoardIntent,registerIntent;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginParamBean = LoginParamBean.getInstance(mContext);
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
    protected AppManager.DataMessageListener addDataMessageListener() {
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
        binding.edPassword.addTextChangedListener(new CustomTextWatcher(0, this));
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
                    if (verifyIntent == null) {
                        verifyIntent = new Intent(PasswordActivity.this, VerificationActivity.class);
                    }
                    verifyIntent.putExtra(KEY_FROM_ACTIVITY, TAG);
                    startActivity(verifyIntent);
                }
                break;
            case R.id.btSubmit:
                if (binding.btSubmit.isButtonEnabled()) {
                    validation();
                }
                break;
        }
    }

    private void validation() {
        int error = loginParamBean.isValidPassword();
        if (error == 1) {
            binding.edPassword.setError(getResString(R.string.validation_password));
            binding.edPassword.requestFocus();
        } else {
            callApi(1);
        }
    }

    private void tempMethod() {
        binding.btSubmit.showProgressBar(true, PROGRESS_TAG_0);
        handler.postDelayed(() -> {
            if (dashBoardIntent == null) {
                dashBoardIntent = new Intent(PasswordActivity.this, DashBoardActivity.class);
            }
            startActivity(dashBoardIntent);
            finishAffinity();
        }, 3000);
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
                apiController.getApiLogin(loginParamBean);
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
        if (tag == ApiConst.LOGIN && result == ApiConst.API_RESULT.SUCCESS && status == 1) {

        } else if (tag == ApiConst.LOGIN && result == ApiConst.API_RESULT.FAIL) {
            enableScreen(true);
            binding.btSubmit.showProgressBar(false, PROGRESS_TAG_0);
        }
    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }

    @Override
    public void onTextChanged(int edTag, String text) {
        if (edTag == 0) {
            password = text;
            loginParamBean.setPassword(password);
        }
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
                break;
            case TYPE_NOT_VERIFIED:
                callApi(2);
                break;
        }
    }
}
