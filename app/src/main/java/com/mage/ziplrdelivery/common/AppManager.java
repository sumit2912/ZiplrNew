package com.mage.ziplrdelivery.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.mage.ziplrdelivery.utils.constant.PrefConst;
import com.mage.ziplrdelivery.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppManager {

    private static AppManager appManager;
    private Context context;
    private HashMap<String, AppCompatActivity> activityList;
    private HashMap<String, DataMessageListener> dataMessageListenerList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AppManager(Context mContext) {
        this.context = mContext;
        activityList = new HashMap<>();
        dataMessageListenerList = new HashMap<>();
        sharedPreferences = context.getSharedPreferences(PrefConst.PREF_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static AppManager getInstance(Context mContext) {
        if (appManager == null) {
            appManager = new AppManager(mContext);
        }
        return appManager;
    }

    public void prefSetStringValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String prefGetStringValue(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void prefSetIntValue(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int prefGetIntValue(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void prefSetBooleanValue(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean prefGetBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void prefRemoveValue(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void prefClearAll() {
        editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    public void prefClearExcept(List<String> keyList) {
        Map<String, ?> keys = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            if (!keyList.contains(entry.getKey())) {
                prefRemoveValue(entry.getKey());
            }
        }
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
