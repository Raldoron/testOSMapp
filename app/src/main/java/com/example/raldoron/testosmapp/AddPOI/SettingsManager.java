package com.example.raldoron.testosmapp.AddPOI;

import android.content.SharedPreferences;

/**
 * Created by Raldoron on 01.12.15.
 */
public class SettingsManager {

    private static final String TAG = SettingsManager.class.getSimpleName();

    private static final SettingsManager _instance = new SettingsManager();
    SharedPreferences mSettings;

    private SettingsManager() {};

    public static SettingsManager getInstance() {
        return _instance;
    }

    public void init(SharedPreferences settings) {
        mSettings = settings;
    }

    public String getString(String key, String defValue) {
        return mSettings.getString(key, defValue);
    }

    public Integer getInt(String key, Integer defValue) {
        return mSettings.getInt(key, defValue);
    }

    public Long getLong(String key, Long defValue) {
        return mSettings.getLong(key, defValue);
    }

    public Boolean getBoolean(String key, Boolean defValue) {
        return mSettings.getBoolean(key, defValue);
    }

    public Float getFloat(String key, Float defValue) {
        return mSettings.getFloat(key, defValue);
    }


    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mSettings.edit();
        if (value != null)
            editor.putString(key, value);
        else
            editor.remove(key);
        editor.commit();
    }

    public void putLong(String key, Long value) {
        SharedPreferences.Editor editor = mSettings.edit();
        if (value != null)
            editor.putLong(key, value);
        else
            editor.remove(key);
        editor.commit();
    }

    public void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = mSettings.edit();
        if (value != null)
            editor.putBoolean(key, value);
        else
            editor.remove(key);
        editor.commit();
    }

    public void putInt(String key, Integer value) {
        SharedPreferences.Editor editor = mSettings.edit();
        if (value != null)
            editor.putInt(key, value);
        else
            editor.remove(key);
        editor.commit();
    }

    public void putFloat(String key, Float value) {
        SharedPreferences.Editor editor = mSettings.edit();
        if (value != null)
            editor.putFloat(key, value);
        else
            editor.remove(key);
        editor.commit();
    }
}
