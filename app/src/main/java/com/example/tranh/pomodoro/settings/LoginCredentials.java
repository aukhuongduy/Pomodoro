package com.example.tranh.pomodoro.settings;

/**
 * Created by Khuong Duy on 1/14/2017.
 */

public class LoginCredentials {
    private String username;
    private  String password;
    boolean remember;

    public LoginCredentials(String username, String password, boolean remember) {
        this.username = username;
        this.password = password;
        this.remember = remember;
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

    @Override
    public String toString() {
        return "LoginCredentials{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", remember=" + remember +
                '}';
    }
}
