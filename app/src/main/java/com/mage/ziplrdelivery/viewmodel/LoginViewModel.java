package com.mage.ziplrdelivery.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mage.ziplrdelivery.param_model.LoginParamBean;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> PhoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    private static MutableLiveData<LoginParamBean> loginLiveData;

    public MutableLiveData<LoginParamBean> getLoginParamBean() {
        if (loginLiveData == null) {
            loginLiveData = new MutableLiveData<>();
        }
        return loginLiveData;
    }

    public void onClick(View view) {
        LoginParamBean loginParamBean = new LoginParamBean();
        loginParamBean.setPhone_number(PhoneNumber.getValue());
        loginParamBean.setPassword(Password.getValue());
        loginLiveData.setValue(loginParamBean);
    }
}
