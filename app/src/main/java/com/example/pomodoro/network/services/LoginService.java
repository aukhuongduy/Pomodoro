package com.example.pomodoro.network.services;

import com.example.pomodoro.network.jsonmodels.LoginResponeJson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Khuong Duy on 1/18/2017.
 */

public interface LoginService {
    @POST("login")
    Call<LoginResponeJson> login(@Body RequestBody body);

}
