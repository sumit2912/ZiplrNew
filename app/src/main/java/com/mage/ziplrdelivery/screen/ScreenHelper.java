package com.mage.ziplrdelivery.screen;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import com.mage.ziplrdelivery.utils.Utils;

import java.util.HashMap;

public class ScreenHelper {

    private HashMap<String, AppCompatActivity> activityList;
    private HashMap<String, DataMessageListener> dataMessageListenerList;
    private HashMap<String, ViewModel> viewModelList;
    private HashMap<String, String> viewModelDestroyerList;

    public ScreenHelper(){
        activityList = new HashMap<>();
        dataMessageListenerList = new HashMap<>();
        viewModelList = new HashMap<>();
        viewModelDestroyerList = new HashMap<>();
    }

    public void addActivity(AppCompatActivity activity) {
        if (!activityList.containsKey(activity.getClass().getSimpleName())) {
            activityList.put(activity.getClass().getSimpleName(), activity);
        }
    }

    public HashMap<String, AppCompatActivity> getActivityList() {
        return activityList;
    }

    public void addDataMessageListener(String screen, DataMessageListener listener) {
        if (!dataMessageListenerList.containsKey(screen)) {
            dataMessageListenerList.put(screen, listener);
        }
    }

    public HashMap<String, ViewModel> getViewModelList(){
        return viewModelList;
    }

    public HashMap<String, String> getViewModelDestroyerList(){
        return viewModelDestroyerList;
    }

    public boolean sendDataMessage(String from, String to, String msg, Data data) {
        if (dataMessageListenerList.containsKey(to)) {
            Utils.print("from = " + from + "    to = " + to + " msg = " + msg + " data = " + (data != null));
            dataMessageListenerList.get(to).onNewDataMessage(from, msg, data);
        }
        return dataMessageListenerList.containsKey(to);
    }

    public void clearAllList() {
        activityList.clear();
        dataMessageListenerList.clear();
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity.getClass().getSimpleName());
    }

    public void removeDataMessageListener(String screen) {
        dataMessageListenerList.remove(screen);
    }

    public interface DataMessageListener {
        void onNewDataMessage(String from, String msg, Data data);
    }
}
