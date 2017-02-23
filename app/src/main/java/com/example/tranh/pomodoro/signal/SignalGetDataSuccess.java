package com.example.tranh.pomodoro.signal;

/**
 * Created by Khuong Duy on 2/24/2017.
 */

public class SignalGetDataSuccess {
    boolean isDone;
    public SignalGetDataSuccess(boolean isDone) {
        this.isDone = isDone;
    }
    public boolean isDone(){
        return isDone;
    }
}