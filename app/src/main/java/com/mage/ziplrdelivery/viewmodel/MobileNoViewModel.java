package com.mage.ziplrdelivery.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mage.ziplrdelivery.param_model.LoginParamBean;

public class MobileNoViewModel extends ViewModel {
    public MutableLiveData<String> PhoneNumber = new MutableLiveData<>();

    private MutableLiveData<LoginParamBean> mobileNoLiveData;

    public MutableLiveData<LoginParamBean> getMobileNoLiveData() {
        if (mobileNoLiveData == null) {
            mobileNoLiveData = new MutableLiveData<>();
        }
        return mobileNoLiveData;
    }

    public void onClick(View view) {
        LoginParamBean.getInstance().setPhone_number(PhoneNumber.getValue());
        mobileNoLiveData.setValue(LoginParamBean.getInstance());
    }
}
