package com.mage.ziplrdelivery.ui;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.common.Screen;
import com.mage.ziplrdelivery.databinding.ActivityVerificationBinding;
import com.mage.ziplrdelivery.utils.constant.ApiConst;
import com.mage.ziplrdelivery.utils.Utils;

public class VerificationActivity extends BaseActivity implements AppManager.DataMessageListener, TextWatcher {

    private ActivityVerificationBinding binding;
    private AppCompatImageView ivBack;
    private Intent changePasswordIntent, dashBoardIntent;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(VerificationActivity.this, R.layout.activity_verification);
        if (dataIntent.hasExtra(KEY_FROM_ACTIVITY)) {
            VALUE_FROM_ACTIVITY = dataIntent.getStringExtra(KEY_FROM_ACTIVITY);
        }
        initUi();
    }

    @Override
    protected Context getContext() {
        return VerificationActivity.this;
    }

    @Override
    protected AppManager.DataMessageListener addDataMessageListener() {
        return VerificationActivity.this;
    }

    @Override
    protected void initUi() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View backView = LayoutInflater.from(this).inflate(R.layout.layout_back_arrow, binding.toolbar, false);
        ivBack = backView.findViewById(R.id.ivBack);
        getSupportActionBar().setCustomView(backView);
        binding.collapsingTl.setTitle(getResources().getString(R.string.verification));
        binding.collapsingTl.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarTitle);
        binding.collapsingTl.setExpandedTitleTextAppearance(R.style.ExpandedAppBarTitle);
        binding.collapsingTl.setCollapsedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        binding.collapsingTl.setExpandedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        ivBack.setOnClickListener(this);
        enableResendButton(false);
        binding.edOtpView.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btResend:

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

    }

    @Override
    public void onResponse(String tag, ApiConst.API_RESULT result, int status, String msg) {

    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        otp = String.valueOf(charSequence);
        if (otp.length() == 4) {
//===========//after Verify User put this code cut and paste=================================================================
            if (VALUE_FROM_ACTIVITY.equals(Screen.REGISTRATION_ACTIVITY)) {
                if (dashBoardIntent == null)
                    dashBoardIntent = new Intent(VerificationActivity.this, DashBoardActivity.class);
                startActivity(dashBoardIntent);
                finishAffinity();
            } else if (VALUE_FROM_ACTIVITY.equals(Screen.PASSWORD_ACTIVITY)) {
                if (changePasswordIntent == null)
                    changePasswordIntent = new Intent(VerificationActivity.this, ChangePasswordActivity.class);
                startActivity(changePasswordIntent);
                finish();
            }
//==========//after Verify User==============================================================================================
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void enableResendButton(boolean enable) {
        binding.btResend.setAlpha(Utils.getFloatFromDimen(VerificationActivity.this, enable ? R.dimen.selected_bt_alpha : R.dimen.unselected_bt_alpha));
        binding.btResend.setOnClickListener(enable ? this : null);
    }
}
