package com.mage.ziplrdelivery.viewmodelfactory.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mage.ziplrdelivery.model.SingletonFactory;
import com.mage.ziplrdelivery.model.data.DashBoardBean;
import com.mage.ziplrdelivery.model.data.NavMenuBean;
import com.mage.ziplrdelivery.utils.Utils;

import java.util.List;

public class NavigationMenuViewModel extends ViewModel {
    public MutableLiveData<String> ProfName = new MutableLiveData<>();
    public MutableLiveData<String> ProfEmail = new MutableLiveData<>();


    private MutableLiveData<DashBoardBean> navigationMenuMutableLiveData;

    public MutableLiveData<DashBoardBean> getNavigationMenuMutableLiveData() {
        if (navigationMenuMutableLiveData == null) {
            navigationMenuMutableLiveData = new MutableLiveData<>();
            try {
                navigationMenuMutableLiveData.setValue(SingletonFactory.getInstance().getDashBoardBean());
                ProfName.setValue(navigationMenuMutableLiveData.getValue().getProfileName());
                ProfEmail.setValue(navigationMenuMutableLiveData.getValue().getProfileEmail());
            } catch (Exception e) {
                Utils.print("NavigationViewModel", e);
            }
        }
        return navigationMenuMutableLiveData;
    }
}
