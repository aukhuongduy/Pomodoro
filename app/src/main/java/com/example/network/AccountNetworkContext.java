package com.example.network;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Khuong Duy on 2/22/2017.
 */

public class AccountNetworkContext extends NetworkContext {
    public static AccountNetworkContext instance = new AccountNetworkContext();
    public AccountNetworkContext() {
        super();
        retrofit = new Retrofit.Builder().
                baseUrl("http://a-task.herokuapp.com/api/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
