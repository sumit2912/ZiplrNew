package com.mage.ziplrdelivery.viewmodelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.MobileNoViewModel;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.PasswordViewModel;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.RegistrationViewModel;

import java.util.HashMap;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private HashMap<String, ViewModel> viewModelHashMap;
    private HashMap<String, String> viewModelDestroyerHashMap;

    public ViewModelFactory(HashMap<String, ViewModel> viewModelHashMap, HashMap<String, String> viewModelDestroyerHashMap) {
        this.viewModelHashMap = viewModelHashMap;
        this.viewModelDestroyerHashMap = viewModelDestroyerHashMap;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class)) { // RegistrationViewModel====================================================
            if (!viewModelHashMap.containsKey(ViewModelIdentifier.KEY_REGISTRATION_VIEW_MODEL)) {
                viewModelHashMap.put(ViewModelIdentifier.KEY_REGISTRATION_VIEW_MODEL, new RegistrationViewModel());
                viewModelDestroyerHashMap.put(ViewModelIdentifier.DES_REGISTRATION_VIEW_MODEL, ViewModelIdentifier.KEY_REGISTRATION_VIEW_MODEL);
            }
            return (T) viewModelHashMap.get(ViewModelIdentifier.KEY_REGISTRATION_VIEW_MODEL);//RegistrationViewModel================================
        } else if (modelClass.isAssignableFrom(MobileNoViewModel.class)) { // MobileNoViewModel=====================================================
            if (!viewModelHashMap.containsKey(ViewModelIdentifier.KEY_MOBILE_NO_VIEW_MODEL)) {
                viewModelHashMap.put(ViewModelIdentifier.KEY_MOBILE_NO_VIEW_MODEL, new MobileNoViewModel());
                viewModelDestroyerHashMap.put(ViewModelIdentifier.DES_MOBILE_NO_VIEW_MODEL, ViewModelIdentifier.KEY_MOBILE_NO_VIEW_MODEL);
            }
            return (T) viewModelHashMap.get(ViewModelIdentifier.KEY_MOBILE_NO_VIEW_MODEL);//MobileNoViewModel=======================================
        } else if (modelClass.isAssignableFrom(PasswordViewModel.class)) { //PasswordViewModel======================================================
            if (!viewModelHashMap.containsKey(ViewModelIdentifier.KEY_PASSWORD_VIEW_MODEL)) {
                viewModelHashMap.put(ViewModelIdentifier.KEY_PASSWORD_VIEW_MODEL, new PasswordViewModel());
                viewModelDestroyerHashMap.put(ViewModelIdentifier.DES_PASSWORD_VIEW_MODEL, ViewModelIdentifier.KEY_PASSWORD_VIEW_MODEL);
            }
            return (T) viewModelHashMap.get(ViewModelIdentifier.KEY_PASSWORD_VIEW_MODEL);
        }
        return super.create(modelClass);
    }
}
