package com.echo.dapc.util.system;

import android.content.SharedPreferences;

import com.echo.dapc.MyApp;


public final class SharedSharedPreferencesUtil {
    static SharedPreferences sp;
    static SharedPreferences.Editor edit;

    static {
        sp = MyApp.getSharedPreferences();
        edit = sp.edit();
    }
    private static void apply() {
        edit.apply();
    }

    public static String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public static Long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public static Integer getInteger(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public static Boolean getBoolean(String key, boolean defaultValue){ return sp.getBoolean(key, defaultValue);}

    public static void addValue(String key, String value) {
        edit.putString(key, value);
        apply();
    }

    public static void addValue(String key, Integer value) {
        edit.putInt(key, value);
        apply();
    }

    public static void addValue(String key, Long value) {
        edit.putLong(key, value);
        apply();
    }

    public static void addValue(String key, Boolean value) {
        edit.putBoolean(key, value);
        apply();
    }

    public static void addValue(String key, Float value) {
        edit.putFloat(key, value);
        apply();
    }

}
