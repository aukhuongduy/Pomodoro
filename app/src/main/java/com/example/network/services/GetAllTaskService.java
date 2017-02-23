package com.example.network.services;

import com.example.network.jsonmodels.TaskResponeJson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Khuong Duy on 2/21/2017.
 */

public interface GetAllTaskService {
    @GET("task")
    Call<List<TaskResponeJson>> getAllTask();
}
