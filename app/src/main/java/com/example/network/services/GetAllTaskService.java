package com.example.network.services;

import com.example.network.jsonmodels.GetAllTaskResponeJson;
import com.example.tranh.pomodoro.settings.SharedPrefs;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Khuong Duy on 2/21/2017.
 */

public interface GetAllTaskService {
    @POST("task")
    Call<List<GetAllTaskResponeJson>> getAllTask();
}
