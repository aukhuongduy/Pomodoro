package com.example.tranh.pomodoro.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Khuong Duy on 2/14/2017.
 */

public interface FragmentListener {
    public void replaceFragment(Fragment fragment, boolean addToBackStack);
}
