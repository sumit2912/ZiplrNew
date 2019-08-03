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
import com.mage.ziplrdelivery.databinding.ActivityMobileNoBinding;
import com.mage.ziplrdelivery.param_model.LoginBean;
import com.mage.ziplrdelivery.utils.constant.ApiConst;

public class MobileNoActivity extends BaseActivity implements AppManager.DataMessageListener {

    private ActivityMobileNoBinding binding;
    private AppCompatImageView ivBack;
    private Intent passwordIntent;
    private LoginBean loginBean;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MobileNoActivity.this, R.layout.activity_mobile_no);
        loginBean = LoginBean.getInstance(mContext);
        loginBean.resetAll();
        initUi();
    }

    @Override
    protected Context getContext() {
        return MobileNoActivity.this;
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
        binding.nonClickable.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btNext:
                validation();
                break;
        }
    }

    private void validation() {
        phoneNumber = super.getEdValue(binding.edMobileNo);
        loginBean.setPhone_number(phoneNumber);
        int error = loginBean.isValidData();
        switch (error) {
            case 0:
                binding.edMobileNo.setError(super.getResString(R.string.validation_mobile_no));
                binding.edMobileNo.requestFocus();
                break;
            case 1:
                binding.edMobileNo.setError(super.getResString(R.string.validation_mobile_no_length).replace("0", "10"));
                binding.edMobileNo.requestFocus();
                break;
            default:
                /*loginBean.printParams();
                if (passwordIntent == null)
                    passwordIntent = new Intent(MobileNoActivity.this;*/

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
