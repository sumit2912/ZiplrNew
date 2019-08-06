package com.mage.ziplrdelivery.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static LoginViewModelFactory viewModelFactory;
    final HashMap<String, ViewModel> viewModelHashMap = new HashMap<>();

    public static LoginViewModelFactory provideViewModelFactory() {
        if (viewModelFactory == null) {
            viewModelFactory = new LoginViewModelFactory();
        }
        return viewModelFactory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MobileNoViewModel.class)) {
            if (!viewModelHashMap.containsKey(MobileNoViewModel.class.getSimpleName())) {
                viewModelHashMap.put(MobileNoViewModel.class.getSimpleName(), new MobileNoViewModel());
            }
            return (T) viewModelHashMap.get(MobileNoViewModel.class.getSimpleName());
        } else if (modelClass.isAssignableFrom(PasswordViewModel.class)) {
            if (!viewModelHashMap.containsKey(PasswordViewModel.class.getSimpleName())) {
                viewModelHashMap.put(PasswordViewModel.class.getSimpleName(), new PasswordViewModel());
            }
            return (T) viewModelHashMap.get(PasswordViewModel.class.getSimpleName());
        }
        return null;
    }
}
