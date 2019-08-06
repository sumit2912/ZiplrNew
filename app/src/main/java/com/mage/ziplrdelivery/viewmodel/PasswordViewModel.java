package com.mage.ziplrdelivery.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mage.ziplrdelivery.param_model.LoginParamBean;
import com.mage.ziplrdelivery.utils.Utils;

public class PasswordViewModel extends ViewModel {
    public MutableLiveData<String> Password = new MutableLiveData<>();

    private MutableLiveData<LoginParamBean> passwordLiveData;

    public MutableLiveData<LoginParamBean> getPasswordLiveData() {
        if (passwordLiveData == null) {
            passwordLiveData = new MutableLiveData<>();
        }
        return passwordLiveData;
    }

    public void onClick(View view) {
        LoginParamBean.getInstance().setPassword(Password.getValue());
        passwordLiveData.setValue(LoginParamBean.getInstance());
        Utils.print("PasswordViewModel","Password = "+Password.getValue());
    }
}
