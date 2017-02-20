package com.example.network;

import com.example.tranh.pomodoro.settings.SharedPrefs;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Khuong Duy on 1/18/2017.
 */

public class NetworkContext {
    protected Retrofit retrofit;

    public NetworkContext(){
        OkHttpClient.Builder httpClient =new OkHttpClient().newBuilder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", "JWT "+ SharedPrefs.getInstance().getAccessToken())
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder().
                baseUrl("http://a-task.herokuapp.com/api/").
                addConverterFactory(GsonConverterFactory.create()).client(client).
                build();
    }
}

