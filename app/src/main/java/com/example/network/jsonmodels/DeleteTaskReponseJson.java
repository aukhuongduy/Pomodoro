package com.example.network.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Khuong Duy on 2/23/2017.
 */

public class DeleteTaskReponseJson {
    @SerializedName("code")
    int code;
    @SerializedName("message")
    String message;

    public DeleteTaskReponseJson(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
