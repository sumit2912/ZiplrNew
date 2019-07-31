package com.mage.ziplrdelivery.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mage.ziplrdelivery.listener.ActivityLayout;
import com.mage.ziplrdelivery.utils.Utils;

public class Callbacks implements Application.ActivityLifecycleCallbacks {

    private AppManager appManager;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        Utils.print("Callbacks", activity.getClass().getSimpleName() + " Created");
        if (activity instanceof ActivityLayout) {
            activity.setContentView(layoutId(activity));
        }
        appManager = AppManager.getInstance(activity);
    }

    private int layoutId(Activity activity) {
        return ((ActivityLayout) activity).getLayoutId();
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Utils.print("Callbacks", activity.getClass().getSimpleName() + " Started");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Utils.print("Callbacks", activity.getClass().getSimpleName() + " Resumed");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Utils.print("Callbacks", activity.getClass().getSimpleName() + " Paused");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Utils.print("Callbacks", activity.getClass().getSimpleName() + " Stopped");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        Utils.print("Callbacks", activity.getClass().getSimpleName() + " onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Utils.print("Callbacks", activity.getClass().getSimpleName() + " Destroyed");
        appManager.removeActivity(activity);
        appManager.removeDataMessageListener(activity.getClass().getSimpleName());
    }
}
