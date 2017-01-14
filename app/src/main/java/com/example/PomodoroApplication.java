package com.example;

import android.app.Application;
import android.util.Log;

import com.example.tranh.pomodoro.settings.SharedPrefs;

/**
 * Created by Khuong Duy on 1/14/2017.
 */

public class PomodoroApplication extends Application{
    private  static  final  String TAG = "PomodoroApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        SharedPrefs.init(this);
    }
}
