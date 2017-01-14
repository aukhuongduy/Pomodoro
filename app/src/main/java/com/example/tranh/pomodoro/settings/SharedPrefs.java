package com.example.tranh.pomodoro.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by Khuong Duy on 1/14/2017.
 */

public class SharedPrefs {
    private static final String SHARED_PRESS_NAME = "SP";
    private static final String LOGIN_KEY = "login";

    private  static  SharedPrefs instance;

    public static SharedPrefs getInstance() {
        return instance;
    }
    public static void init(Context context){
        instance = new SharedPrefs(context);
    }

    private Gson gson = new Gson();
    private SharedPreferences sharedPreferences;

    public SharedPrefs(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PRESS_NAME, Context.MODE_PRIVATE);
    }

    public void put(LoginCredentials loginCredentials) {

        String json = gson.toJson(loginCredentials);
        this.sharedPreferences.edit().putString(LOGIN_KEY, json).commit();
    }

    public LoginCredentials getLoginCredentials() {
        LoginCredentials loginCredentials;
        String loginJSON = this.sharedPreferences.getString(LOGIN_KEY, null);
        if (loginJSON == null) {
            return null;
        } else {
            loginCredentials = gson.fromJson(loginJSON, LoginCredentials.class);
        }
        return loginCredentials;
    }

}
