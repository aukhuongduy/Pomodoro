package com.example.pomodoro.network.services;

import com.example.pomodoro.network.jsonmodels.DeleteTaskReponseJson;
import com.example.pomodoro.network.jsonmodels.TaskResponeJson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Khuong Duy on 2/21/2017.
 */

public interface TaskService {
    @POST("task")
    Call<TaskResponeJson> addNewTask(@Body RequestBody body);
    @PUT("task/{local_id}")
    Call<TaskResponeJson> editATask(@Path("local_id")String local_id, @Body RequestBody body);
    @DELETE("task/{local_id}")
    Call<DeleteTaskReponseJson> deleteTask(@Path("local_id") String local_id);
}
