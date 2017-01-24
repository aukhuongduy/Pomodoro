package com.example.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Khuong Duy on 1/18/2017.
 */

public class NetworkContext {
    private Retrofit retrofit;
    public NetworkContext(){
        retrofit = new Retrofit.Builder().
                baseUrl("http://a-task.herokuapp.com/api/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}

