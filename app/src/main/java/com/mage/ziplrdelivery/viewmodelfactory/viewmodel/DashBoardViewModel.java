package com.mage.ziplrdelivery.viewmodelfactory.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mage.ziplrdelivery.model.data.DashBoardBean;

public class DashBoardViewModel extends ViewModel {
    private MutableLiveData<DashBoardBean> dashBoardMutableLiveData;

    public MutableLiveData<DashBoardBean> getDashBoardMutableLiveData() {
        if (dashBoardMutableLiveData == null) {
            dashBoardMutableLiveData = new MutableLiveData<>();
        }
        return dashBoardMutableLiveData;
    }
}
