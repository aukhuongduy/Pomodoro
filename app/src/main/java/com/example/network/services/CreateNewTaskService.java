package com.example.network.services;

import com.example.network.jsonmodels.TaskResponeJson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Khuong Duy on 2/21/2017.
 */

public interface CreateNewTaskService {
    @POST("task")
    Call<TaskResponeJson> addNewTask(@Body RequestBody body);
}
