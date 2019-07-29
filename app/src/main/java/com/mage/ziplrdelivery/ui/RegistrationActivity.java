package com.mage.ziplrdelivery.ui;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import com.mage.ziplrdelivery.CountryPicker.Country;
import com.mage.ziplrdelivery.CountryPicker.CountryPicker;
import com.mage.ziplrdelivery.CountryPicker.CountryPickerListener;
import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.databinding.ActivityRegistrationBinding;
import com.mage.ziplrdelivery.utils.Const;

import java.util.Collections;
import java.util.List;

public class RegistrationActivity extends BaseActivity implements AppManager.DataMessageListener {

    private ActivityRegistrationBinding binding;
    private AppCompatImageView ivBack;
    private CountryPicker mCountryPicker;
    private String countryCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RegistrationActivity.this, R.layout.activity_registration);
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
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View backView = LayoutInflater.from(this).inflate(R.layout.layout_back_arrow, binding.toolbar, false);
        ivBack = backView.findViewById(R.id.ivBack);
        getSupportActionBar().setCustomView(backView);
        binding.collapsingTl.setTitle(getResources().getString(R.string.registration));
        binding.collapsingTl.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarTitle);
        binding.collapsingTl.setExpandedTitleTextAppearance(R.style.ExpandedAppBarTitle);
        binding.collapsingTl.setCollapsedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        binding.collapsingTl.setExpandedTitleTypeface(Typeface.createFromAsset(getAssets(), "font/ProximaNova-Bold.ttf"));
        ivBack.setOnClickListener(this);
        binding.llCountryCode.setOnClickListener(this);
        mCountryPicker = CountryPicker.newInstance(getResources().getString(R.string.lbl_select_country_code));
        // You can limit the displayed countries
        List<Country> countryList = Country.getAllCountries();
        Collections.sort(countryList, (s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
        mCountryPicker.setCountriesList(countryList);
        setListener();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.llCountryCode:
                if(!mCountryPicker.isAdded())
                mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
                break;
        }
    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }

    private void setListener() {
        mCountryPicker.setListener((name, code, dialCode, flagDrawableResID) -> {
            binding.tvCode.setText(dialCode);
            countryCode = dialCode;
            binding.ivFlag.setImageResource(flagDrawableResID);
            mCountryPicker.dismiss();
        });

        getUserCountryInfo();
    }

    private void getUserCountryInfo() {
        Country country = Country.getCountryFromSIM(RegistrationActivity.this);
        if (country != null) {
            binding.ivFlag.setImageResource(country.getFlag());
            binding.tvCode.setText(country.getDialCode());
            countryCode = country.getDialCode();
        } else {
            Country us = new Country("IN", "India", "+91", R.drawable.flag_in);
            binding.ivFlag.setImageResource(us.getFlag());
            binding.tvCode.setText(us.getDialCode());
            countryCode = us.getDialCode();
        }
    }

}
