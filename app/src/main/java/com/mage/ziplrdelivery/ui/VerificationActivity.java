package com.mage.ziplrdelivery.ui;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

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
import com.mage.ziplrdelivery.databinding.ActivityVerificationBinding;
import com.mage.ziplrdelivery.uc.CustomTextWatcher;
import com.mage.ziplrdelivery.utils.constant.ApiConst;
import com.mage.ziplrdelivery.utils.Utils;

import java.util.Objects;

public class VerificationActivity extends BaseActivity implements AppManager.DataMessageListener, CustomTextWatcher.TextWatcherListener {

    private ActivityVerificationBinding binding;
    private AppCompatImageView ivBack;
    private Intent changePasswordIntent, dashBoardIntent;
    private String verifyOtp;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(VerificationActivity.this, R.layout.activity_verification);
        if (dataIntent.hasExtra(KEY_FROM_ACTIVITY)) {
            VALUE_FROM_ACTIVITY = dataIntent.getStringExtra(KEY_FROM_ACTIVITY);
        }
        if (dataIntent.hasExtra(KEY_FP_CLICK)) {
            VALUE_FP_CLICK = true;
        }
        if (dataIntent.hasExtra(KEY_BEAN_1)) {
            result = (Result) Objects.requireNonNull(dataIntent.getExtras()).getSerializable(KEY_BEAN_1);
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowCustomEnabled(true);
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
        binding.edOtpView.addTextChangedListener(new CustomTextWatcher(0, this));
        binding.nonClickable.setOnClickListener(this);
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
        if (isInternet) {
            if (tag == 1) {
                enableScreen(false);
                apiController.getApiVerification(result);
            } else if (tag == 2) {

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
        Utils.print(Screen.VERIFICATION_ACTIVITY, "tag = " + tag + " result = " + result + " status = " + status + " msg =" + msg);
        if (tag == ApiConst.AUTH_VERIFY_OTP && result == ApiConst.API_RESULT.SUCCESS && status == 1) {
            if (VALUE_FROM_ACTIVITY.equals(Screen.REGISTRATION_ACTIVITY)) {
                if (dashBoardIntent == null)
                    dashBoardIntent = new Intent(VerificationActivity.this, DashBoardActivity.class);
                startActivity(dashBoardIntent);
                finishAffinity();
            } else if (VALUE_FROM_ACTIVITY.equals(Screen.PASSWORD_ACTIVITY) && VALUE_FP_CLICK) {
                if (changePasswordIntent == null)
                    changePasswordIntent = new Intent(VerificationActivity.this, ChangePasswordActivity.class);
                startActivity(changePasswordIntent);
                finish();
            }
        } else if (tag == ApiConst.AUTH_VERIFY_OTP && result == ApiConst.API_RESULT.FAIL) {
            if (status < 2) {
                enableScreen(true);
            }
            if (status == 5) {
                if (VALUE_FROM_ACTIVITY.equals(Screen.PASSWORD_ACTIVITY) && VALUE_FP_CLICK) {
                    if (changePasswordIntent == null)
                        changePasswordIntent = new Intent(VerificationActivity.this, ChangePasswordActivity.class);
                    startActivity(changePasswordIntent);
                    finish();
                } else {
                    if (VALUE_FROM_ACTIVITY.equals(Screen.PASSWORD_ACTIVITY)) {
                        finish();
                    }
                }
            }
        }
    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }

    private void enableResendButton(boolean enable) {
        binding.btResend.setAlpha(Utils.getFloatFromDimen(VerificationActivity.this, enable ? R.dimen.selected_bt_alpha : R.dimen.unselected_bt_alpha));
        binding.btResend.setOnClickListener(enable ? this : null);
    }

    @Override
    public void onTextChanged(int edTag, String text) {
        switch (edTag) {
            case 0:
                verifyOtp = String.valueOf(text);
                if (verifyOtp.length() == 4) {
                    callApi(1);
                }
                break;
        }
    }
}
