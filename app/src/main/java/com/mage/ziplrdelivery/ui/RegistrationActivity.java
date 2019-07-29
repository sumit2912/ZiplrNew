package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mage.ziplrdelivery.CountryPicker.Country;
import com.mage.ziplrdelivery.CountryPicker.CountryPicker;
import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.common.Data;
import com.mage.ziplrdelivery.databinding.ActivityRegistrationBinding;
import com.mage.ziplrdelivery.param_model.RegistrationParamBean;
import com.mage.ziplrdelivery.utils.Const;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.viewmodel.RegistrationViewModel;

import java.util.Collections;
import java.util.List;

public class RegistrationActivity extends BaseActivity implements AppManager.DataMessageListener, Observer<RegistrationParamBean> {

    private ActivityRegistrationBinding binding;
    private AppCompatImageView ivBack;
    private CountryPicker mCountryPicker;
    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RegistrationActivity.this, R.layout.activity_registration);
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setRegistrationViewModel(registrationViewModel);
        registrationViewModel.getRegistrationParamBean().observe(this, this);
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
                if (!mCountryPicker.isAdded())
                    mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
                break;
        }
    }

    @Override
    public void onChanged(RegistrationParamBean registrationParamBean) {
        int error = registrationParamBean.isValidData();
        switch (error) {
            case 0:
                binding.edName.setError(getResources().getString(R.string.validation_name));
                break;
            case 1:
                binding.edEmail.setError(getResources().getString(R.string.validation_email));
                break;
            case 2:
                binding.edPassword.setError(getResources().getString(R.string.validation_password));
                break;
            case 3:
                binding.edCPassword.setError(getResources().getString(R.string.validation_c_password));
                break;
            case 4:
                binding.edCPassword.setError(getResources().getString(R.string.validation_password_match));
                break;
            case 5:
                binding.tvCode.setError(getResources().getString(R.string.validation_country_code));
                break;
            case 6:
                binding.edPhoneNo.setError(getResources().getString(R.string.validation_phone_no));
                break;
            default:
                Utils.toast(this,"You can submit",false);
                break;
        }

    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }

    private void setListener() {
        mCountryPicker.setListener((name, code, dialCode, flagDrawableResID) -> {
            binding.tvCode.setText(dialCode);
            registrationViewModel.CountryCode.setValue(dialCode);
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
            registrationViewModel.CountryCode.setValue(country.getDialCode());
        } else {
            Country india = new Country("IN", "India", "+91", R.drawable.flag_in);
            binding.ivFlag.setImageResource(india.getFlag());
            binding.tvCode.setText(india.getDialCode());
            registrationViewModel.CountryCode.setValue(india.getDialCode());
        }
    }
}
