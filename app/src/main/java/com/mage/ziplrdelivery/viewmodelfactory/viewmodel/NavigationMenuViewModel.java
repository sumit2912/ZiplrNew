package com.mage.ziplrdelivery.viewmodelfactory.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mage.ziplrdelivery.model.SingletonFactory;
import com.mage.ziplrdelivery.model.data.DashBoardBean;
import com.mage.ziplrdelivery.utils.Utils;

public class NavigationMenuViewModel extends ViewModel {
    public MutableLiveData<String> ProfName = new MutableLiveData<>();
    public MutableLiveData<String> ProfEmail = new MutableLiveData<>();

    private MutableLiveData<DashBoardBean> navigationMenuMutableLiveData;

    public MutableLiveData<DashBoardBean> getNavigationMenuMutableLiveData() {
        if (navigationMenuMutableLiveData == null) {
            navigationMenuMutableLiveData = new MutableLiveData<>();
        }
        return navigationMenuMutableLiveData;
    }

    public static class NavMenu{
        public MutableLiveData<String> ItemName = new MutableLiveData<>();
        public MutableLiveData<Integer> ItemDrawable = new MutableLiveData<>();
    }
}
