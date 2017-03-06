package com.example.pomodoro.network.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Khuong Duy on 1/25/2017.
 */

public class RegisterBodyJson {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public RegisterBodyJson(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
