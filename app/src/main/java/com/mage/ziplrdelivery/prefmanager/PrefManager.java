package com.mage.ziplrdelivery.prefmanager;

import android.content.SharedPreferences;

import java.util.List;
import java.util.Map;

public class PrefManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefManager(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        this.sharedPreferences = sharedPreferences;
        this.editor = editor;
    }

    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void setLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, -1);
    }

    public void removeValue(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void clearAll() {
        editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    public void clearExcept(List<String> keyList) {
        Map<String, ?> keys = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            if (!keyList.contains(entry.getKey())) {
                removeValue(entry.getKey());
            }
        }
    }
}
