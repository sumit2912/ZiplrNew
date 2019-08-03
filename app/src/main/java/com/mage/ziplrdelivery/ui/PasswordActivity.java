package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.common.Screen;
import com.mage.ziplrdelivery.databinding.ActivityPasswordBinding;
import com.mage.ziplrdelivery.param_model.LoginBean;
import com.mage.ziplrdelivery.utils.constant.ApiConst;

public class PasswordActivity extends BaseActivity implements AppManager.DataMessageListener {

    private static final int PROGRESS_TAG = 0;
    private ActivityPasswordBinding binding;
    private AppCompatImageView ivBack;
    private Intent verifyIntent, dashBoardIntent;
    private LoginBean loginBean;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(PasswordActivity.this, R.layout.activity_password);
        loginBean = LoginBean.getInstance(mContext);
        initUi();
    }

    @Override
    protected Context getContext() {
        return PasswordActivity.this;
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
        binding.nonClickable.setOnClickListener(this);
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
                    VALUE_FROM_ACTIVITY = Screen.PASSWORD_ACTIVITY;
                    verifyIntent.putExtra(KEY_FROM_ACTIVITY, VALUE_FROM_ACTIVITY);
                    startActivity(verifyIntent);
                }
                break;
            case R.id.btSubmit:
                validation();
                break;
        }
    }

    private void validation() {
        password = super.getEdValue(binding.edPassword);
        loginBean.setPassword(password);
        int error = loginBean.isValidData();
        switch (error) {
            case 2:
                binding.edPassword.setError(super.getResString(R.string.validation_password));
                binding.edPassword.requestFocus();
                break;
            default:
                loginBean.printParams();
                tempMethod();
                break;
        }
    }

    private void tempMethod() {
        binding.btSubmit.showProgressBar(true, PROGRESS_TAG);
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

    }

    @Override
    protected void enableScreen(boolean enable) {
        binding.nonClickable.setVisibility(enable ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onResponse(String tag, ApiConst.API_RESULT result, int status, String msg) {

    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }
}
