package com.mage.ziplrdelivery.viewmodelfactory.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mage.ziplrdelivery.model.SingletonFactory;
import com.mage.ziplrdelivery.model.param.LoginParamBean;
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
        SingletonFactory.getInstance().getLoginParamBean().setPassword(Password.getValue());
        passwordLiveData.setValue(SingletonFactory.getInstance().getLoginParamBean());
        Utils.print("PasswordViewModel","Password = "+Password.getValue());
    }
}
