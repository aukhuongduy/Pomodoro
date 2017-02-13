package com.example.network.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Khuong Duy on 1/18/2017.
 */

public class LoginResponeJson {
    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
