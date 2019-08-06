package com.mage.ziplrdelivery.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mage.ziplrdelivery.param_model.RegistrationParamBean;

public class RegistrationViewModel extends ViewModel {
    public MutableLiveData<String> Name = new MutableLiveData<>();
    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<String> ConfirmPassword = new MutableLiveData<>();
    public MutableLiveData<String> CountryCode = new MutableLiveData<>();
    public MutableLiveData<String> PhoneNumber = new MutableLiveData<>();

    private MutableLiveData<RegistrationParamBean> registrationLiveData;

    public MutableLiveData<RegistrationParamBean> getRegistrationLiveData() {
        if (registrationLiveData == null) {
            registrationLiveData = new MutableLiveData<>();
        }
        return registrationLiveData;
    }

    public void onClick(View view) {
        RegistrationParamBean registrationParamBean = new RegistrationParamBean(Name.getValue(), Email.getValue(), Password.getValue(),
                ConfirmPassword.getValue(), CountryCode.getValue(), PhoneNumber.getValue());
        registrationLiveData.setValue(registrationParamBean);
    }
}
