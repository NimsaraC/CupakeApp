package com.nimsara.cupcakeapp.login;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public static final String APP_NAME = "Cupcake App";
    public static final String KEY_NAME = "User_Name";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_PIN = "PIN";
    public static final String KEY_STATUS = "Status";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String STREET = "street";
    public static final String CITY = "city";
    public static final String ZIP = "zip";

    public SharedPreference() {
        super();
    }

    public void SaveString(Context context, String value, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public void SaveInt(Context context, int value, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void SaveBoolean(Context context, Boolean value, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getString(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(APP_NAME,Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }
    public boolean GetBoolean(Context context, String key) {

        SharedPreferences preferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }
    public int GetInt(Context context, String key)
    {

        SharedPreferences preferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(key,-1);
    };

}
