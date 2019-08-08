package com.mage.ziplrdelivery.viewmodelfactory.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashBoardViewModel extends ViewModel {
    public MutableLiveData<String> ProfName = new MutableLiveData<>();
    public MutableLiveData<String> ProfEmail = new MutableLiveData<>();


}
