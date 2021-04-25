package com.bihanitech.shikshyaprasasak.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.bihanitech.shikshyaprasasak.utility.Constant;

public class SharedPrefsHelper {
    private static com.bihanitech.shikshyaprasasak.database.SharedPrefsHelper spfInstance;
    private static SharedPreferences sInstance;

    public SharedPrefsHelper(SharedPreferences shp){
        sInstance  = shp;

    }

    public static synchronized com.bihanitech.shikshyaprasasak.database.SharedPrefsHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (spfInstance == null) {
            spfInstance = new com.bihanitech.shikshyaprasasak.database.SharedPrefsHelper(context.getSharedPreferences(Constant.SHARED_PREFS,Context.MODE_PRIVATE));
        }
        return spfInstance;
    }


    public void saveValue(String key,String value){
        sInstance.edit().putString(key,value).apply();
    }

    public String getValue(String key,String defaultValue){
        return sInstance.getString(key,defaultValue);
    }

    public void saveValue(String key,Boolean value){
        sInstance.edit().putBoolean(key,value).apply();
    }

    public Boolean getValue(String key,Boolean defaultValue){
        return sInstance.getBoolean(key,defaultValue);
    }

    public void saveValue(String key, int value){
        sInstance.edit().putInt(key,value).apply();
    }

    public int getValue(String key,int defaultValue){
        return sInstance.getInt(key,defaultValue);
    }

    public void clear() {
        sInstance.edit().clear().apply();
    }

}
