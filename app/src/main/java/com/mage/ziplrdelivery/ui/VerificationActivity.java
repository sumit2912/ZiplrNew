package com.mage.ziplrdelivery.ui;

import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.common.MyTimerTask;
import com.mage.ziplrdelivery.common.Screen;
import com.mage.ziplrdelivery.data_model.Result;
import com.mage.ziplrdelivery.databinding.ActivityVerificationBinding;
import com.mage.ziplrdelivery.uc.CustomTextWatcher;
import com.mage.ziplrdelivery.utils.constant.ApiConst;
import com.mage.ziplrdelivery.utils.Utils;

import java.util.Objects;
import java.util.Timer;

public class VerificationActivity extends BaseActivity implements AppManager.DataMessageListener, CustomTextWatcher.TextWatcherListener, MyTimerTask.TaskListener {

    private static final String TAG = Screen.VERIFICATION_ACTIVITY;
    private ActivityVerificationBinding binding;
    private AppCompatImageView ivBack;
    private Intent changePasswordIntent, dashBoardIntent, passwordIntent;
    private String verifyOtp;
    private Result result;
    private Timer timer;
    private MyTimerTask myTimerTask;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (dataIntent.hasExtra(KEY_FROM_ACTIVITY)) {
            VALUE_FROM_ACTIVITY = dataIntent.getStringExtra(KEY_FROM_ACTIVITY);
        }
        if (dataIntent.hasExtra(KEY_FP_CLICK)) {
            VALUE_FP_CLICK = true;
        }
        if (dataIntent.hasExtra(KEY_RESULT_BEAN)) {
            result = new Result();
            Result tempResult = (Result) Objects.requireNonNull(dataIntent.getExtras()).getSerializable(KEY_RESULT_BEAN);
            result.setPhoneNumber(tempResult.getPhoneNumber());
            if(!VALUE_FP_CLICK)
            result.setPassword(tempResult.getPassword());
        }
        initUi();
    }

    @Override
    protected Context getContext() {
        return VerificationActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verification;
    }

    @Override
    protected <S> S getViewBinding(S s) {
        binding = (ActivityVerificationBinding) s;
        return (S) binding;
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
        binding.edOtpView.addTextChangedListener(new CustomTextWatcher(0, this));
        binding.nonClickable.setOnClickListener(null);
        initTimerTask();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btResend:
                callApi(2);
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
            Utils.hideKeyBoardFromView(mContext);
            if (tag == 1) {
                enableScreen(false);
                showProgressBar(true);
                apiController.getApiVerification(result);
            } else if (tag == 2) {
                enableScreen(false);
                binding.btResend.showProgressBar(true,PROGRESS_TAG_0);
                apiController.getApiSendOTP(result.getPhoneNumber());
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
        if (tag == ApiConst.VERIFY_OTP && result == ApiConst.API_RESULT.SUCCESS && status == 1) {
            enableScreen(true);
            showProgressBar(false);
            if (VALUE_FROM_ACTIVITY.equals(Screen.REGISTRATION_ACTIVITY) && !VALUE_FP_CLICK) {
                Result data = apiController.getResultData();
                if (data != null) {
                    Utils.storeLoginData(appManager, data);
                }
                if (dashBoardIntent == null)
                    dashBoardIntent = new Intent(VerificationActivity.this, DashBoardActivity.class);
                startActivity(dashBoardIntent);
                finishAffinity();
            }
        } else if (tag == ApiConst.VERIFY_OTP && result == ApiConst.API_RESULT.FAIL) {
            enableScreen(true);
            showProgressBar(false);
            if (status == 5) {
                if (VALUE_FROM_ACTIVITY.equals(Screen.PASSWORD_ACTIVITY) && VALUE_FP_CLICK) {
                    if (changePasswordIntent == null)
                        changePasswordIntent = new Intent(VerificationActivity.this, ChangePasswordActivity.class);
                    startActivity(changePasswordIntent);
                    finish();
                } else if (VALUE_FROM_ACTIVITY.equals(Screen.PASSWORD_ACTIVITY)) {
                    finish();
                } else {
                    passwordIntent = new Intent(VerificationActivity.this, PasswordActivity.class);
                    startActivity(passwordIntent);
                    finish();
                }
            }
        }

        if (tag == ApiConst.SEND_OTP && result == ApiConst.API_RESULT.SUCCESS && status == 1) {
            enableScreen(true);
            binding.btResend.showProgressBar(false,PROGRESS_TAG_0);
            Utils.toast(mContext, msg, false);
            initTimerTask();
        } else if (tag == ApiConst.SEND_OTP && result == ApiConst.API_RESULT.FAIL) {
            enableScreen(true);
            binding.btResend.showProgressBar(false,PROGRESS_TAG_0);
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
                    result.setOtp(verifyOtp);
                    callApi(1);
                }
                break;
        }
    }

    @Override
    public void onNegativeClicked(String type) {

    }

    @Override
    public void onPositiveClicked(String type) {

    }

    private void initTimerTask() {
        enableResendButton(false);
        counter = 30;
        timer = new Timer();
        myTimerTask = new MyTimerTask(VerificationActivity.this);
        binding.tvTimer.setText("00:" + counter);
        timer.schedule(myTimerTask, 0,1000);
    }

    @Override
    public void updateUi() {
        binding.tvTimer.post(() -> {
            if (counter == 0) {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                    myTimerTask = null;
                }
                enableResendButton(true);
            }
            binding.tvTimer.setText("00:" + Utils.padInt(counter));
            if (counter > 0)
                counter--;
        });
    }
}
