package com.example.pomodoro.network.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Khuong Duy on 1/25/2017.
 */

public class RegisterResponeJson {
    @SerializedName("access_token")
    String access_token;

    public RegisterResponeJson(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "RegisterResponeJson{" +
                "access_token='" + access_token + '\'' +
                '}';
    }
}
