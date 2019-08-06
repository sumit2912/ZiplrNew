package com.mage.ziplrdelivery.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mage.ziplrdelivery.utils.Utils;

import java.util.HashMap;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static final String KEY = "LoginViewModel";
    final HashMap<String, ViewModel> loginViewModelMap = new HashMap<>();
    private static LoginViewModelFactory viewModelFactory;

    public static LoginViewModelFactory provideViewModelFactory() {
        if (viewModelFactory == null) {
            viewModelFactory = new LoginViewModelFactory();
        }
        return viewModelFactory;
    }

    public void clearLoginViewModel(){
        this.loginViewModelMap.clear();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            if (loginViewModelMap.containsKey(KEY)) {
                return (T) loginViewModelMap.get(KEY);
            } else {
                addViewModel(KEY, new LoginViewModel());
                return getViewModel(KEY);
            }
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    private <T extends ViewModel> T getViewModel(String key) {
        return (T) loginViewModelMap.get(key);
    }

    private void addViewModel(String key, ViewModel viewModel) {
        loginViewModelMap.put(key, viewModel);
    }
}
