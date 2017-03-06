package com.example.pomodoro.signal;

/**
 * Created by Khuong Duy on 3/7/2017.
 */

public class SignalTokenHasExpired {
    private boolean isExpired;

    public SignalTokenHasExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }
}
