package com.mage.ziplrdelivery.viewmodelfactory.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mage.ziplrdelivery.model.SingletonFactory;
import com.mage.ziplrdelivery.model.param.LoginParamBean;

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
        SingletonFactory.getInstance().getLoginParamBean().setPhone_number(PhoneNumber.getValue());
        mobileNoLiveData.setValue(SingletonFactory.getInstance().getLoginParamBean());
    }
}
