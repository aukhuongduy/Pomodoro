package com.example;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.example.pomodoro.database.DBContext;
import com.example.pomodoro.services.PomodoroService;
import com.example.pomodoro.settings.SharedPrefs;

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
        DBContext.instance.onCreate(this);
        Intent intent = new Intent(this, PomodoroService.class);
        startService(intent);
    }
}
