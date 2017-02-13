package com.example.tranh.pomodoro.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Khuong Duy on 2/13/2017.
 */

public class ManagerFragment {
    public FragmentManager fm ;
    public FragmentTransaction ft;
    public int layoutReplace;

    public ManagerFragment(FragmentManager fm,int layoutReplace) {
        this.fm =fm;
        this.layoutReplace = layoutReplace;
        ft =fm.beginTransaction();
    }

    public FragmentTransaction getFragmentTransaction(){
        return this.ft;
    }

    public void replaceFragment(Fragment fragment, boolean addToBackstack){
        ft.replace(layoutReplace,fragment);
        if(addToBackstack){
            ft.addToBackStack(null);
        }
        ft.commit();
    }
}
