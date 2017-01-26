package com.example.network.services;

import com.example.network.jsonmodels.RegisterResponeJson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Khuong Duy on 1/25/2017.
 */

public interface RegisterService {
    @POST("register")
    Call<RegisterResponeJson> register(@Body RequestBody body);
}
