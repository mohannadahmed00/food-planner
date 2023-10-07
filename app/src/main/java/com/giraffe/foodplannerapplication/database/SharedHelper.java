package com.giraffe.foodplannerapplication.database;

import android.content.Context;
import android.content.SharedPreferences;

import io.reactivex.rxjava3.core.Completable;

public class SharedHelper {
    private static SharedHelper instance;
    private static SharedPreferences shared;

    public static SharedHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedHelper(context);
        }
        return instance;
    }

    private SharedHelper(Context context) {
        shared = context.getSharedPreferences("local_shared", Context.MODE_PRIVATE);//within the whole project, and i should get it by its name
    }

    public void store(SharedKeys key, String value) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(key.toString(), value);
        editor.apply();
    }

    public String read(SharedKeys key) {
        return shared.getString(key.toString(), null);
    }

    public void clearAll(){
        shared.edit().clear().apply();
    }

}
