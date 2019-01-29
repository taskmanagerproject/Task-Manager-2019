package com.example.nikhil.taskmanager.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    public SharedPreferences TM_Preferences;

    private static PreferenceHelper instance;
    public static  final String IS_LOGIN = "is_Login";
    public static boolean check;

    private PreferenceHelper(Context context){
        TM_Preferences=context.getSharedPreferences("TM_Preferences",Context.MODE_PRIVATE);
    }

    public static PreferenceHelper getInstance(Context context)
    {
        if(instance==null){
            instance=new PreferenceHelper(context);
        }
        return instance;
    }
    public void putBoolean(String key,boolean defaultvalue){
        SharedPreferences.Editor editor = TM_Preferences.edit();
        editor.putBoolean(key, defaultvalue);
        editor.apply();
        editor.commit();
    }
    public boolean getBoolean(String Key,boolean defaultvalue){
        return TM_Preferences.getBoolean(Key,defaultvalue);
    }
    public void putString(String key, String Value){
        SharedPreferences.Editor editor = TM_Preferences.edit();
        editor.putString(key,Value);
        editor.apply();
        editor.commit();
    }
    public String getString(String key, String defaultValue){
        return TM_Preferences.getString(key, defaultValue);
    }
}
