package com.example.pomodoro.settings;

/**
 * Created by Khuong Duy on 1/14/2017.
 */

public class LoginCredentials {
    private String username;
    private  String password;
    private String accessToken;
    boolean remember;

    public LoginCredentials(String username, String password,String accessToken, boolean remember) {
        this.username = username;
        this.password = password;
        this.remember = remember;
        this.accessToken = accessToken;
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

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return "LoginCredentials{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", remember=" + remember +
                '}';
    }
}
