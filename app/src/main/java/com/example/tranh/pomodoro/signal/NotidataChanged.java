package com.example.tranh.pomodoro.signal;

/**
 * Created by Khuong Duy on 2/26/2017.
 */

public class NotidataChanged {
    boolean isChanged;

    public boolean isChanged() {
        return isChanged;
    }

    public NotidataChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }
}
