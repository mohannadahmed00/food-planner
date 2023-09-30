package com.giraffe.foodplannerapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedHelper {
    private static SharedHelper instance;
    private static SharedPreferences shared;

    public static SharedHelper getInstance(Context context) {
        if (instance==null){
            instance = new SharedHelper(context);
        }
        return instance;
    }

    private SharedHelper(Context context){
        shared = context.getSharedPreferences("local_shared",Context.MODE_PRIVATE);//within the whole project, and i should get it by its name
    }

    public static void store(String key,String value) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String read(String key) {
        return shared.getString(key, null);
    }
}
