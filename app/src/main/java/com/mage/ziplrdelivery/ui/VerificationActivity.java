package com.mage.ziplrdelivery.ui;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.databinding.ActivityVerificationBinding;
import com.mage.ziplrdelivery.utils.Const;

public class VerificationActivity extends BaseActivity implements AppManager.DataMessageListener, TextWatcher {

    private ActivityVerificationBinding binding;
    private AppCompatImageView ivBack;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(VerificationActivity.this, R.layout.activity_verification);
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
        binding.btResend.setOnClickListener(this);
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

    }

    @Override
    protected void callApi(int tag) {

    }

    @Override
    public void onResponse(String tag, Const.API_RESULT result, int status, String msg) {

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
            //Verify User
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
