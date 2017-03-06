package com.example.pomodoro.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Khuong Duy on 2/22/2017.
 */

public class LoggerInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        RequestBody body = original.body();
        if(body!=null){
            Log.d(TAG, String.format("intercept: %s", body));
        }
        okhttp3.Headers headers = original.headers();
        if(headers != null){
            Log.d(TAG, String.format("intercept: %s", headers));
        }
        Request request = original.newBuilder().
                method(original.method(), original.body())
                .build();
        return chain.proceed(request);

    }
}
