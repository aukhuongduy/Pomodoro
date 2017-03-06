package com.example.pomodoro.signal;

/**
 * Created by Khuong Duy on 2/27/2017.
 */

public class SignalOnFailureNetwork {
    private boolean isFailed;

    public SignalOnFailureNetwork(boolean isFailed) {
        this.isFailed = isFailed;
    }

    public boolean isFailed() {
        return isFailed;
    }

    public void setFailed(boolean failed) {
        isFailed = failed;
    }
}
