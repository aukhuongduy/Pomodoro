package com.example.network;

import android.util.Log;

import com.example.tranh.pomodoro.settings.SharedPrefs;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;

import static android.content.ContentValues.TAG;

/**
 * Created by Khuong Duy on 1/18/2017.
 */

public class NetworkContext {
    protected Retrofit retrofit;

    public NetworkContext(){

    }



}

