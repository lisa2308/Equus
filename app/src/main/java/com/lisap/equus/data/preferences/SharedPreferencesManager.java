package com.lisap.equus.data.preferences;


import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.lisap.equus.data.entities.Stable;
import com.lisap.equus.utils.Constants;

public class SharedPreferencesManager {

    public static String getString(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key,null);
    }

    public static void putString(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static int getInt(Context context, String key, int defaultvalue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultvalue);
    }

    public static void putInt(Context context, String key, int value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    public static Stable getStable(Context context) {
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.PREF_STABLE_ID, "");
        return gson.fromJson(json, Stable.class);
    }

    public static void putStable(Context context, Stable stable) {
        Gson gson = new Gson();
        String json = gson.toJson(stable);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Constants.PREF_STABLE_ID, json).apply();
    }
}
