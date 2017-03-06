package com.example.pomodoro.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tranh.pomodoro.R;
import com.example.pomodoro.adapters.ColorAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseColor extends Fragment {

    @BindView(R.id.rv_colors)
    RecyclerView rv_colors;
    public ChooseColor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.fragment_blank, container, false);
        setUI(view);
        return view;
    }

    private void setUI(View view) {
        ButterKnife.bind(this,view);

        ColorAdapter colorAdapter = new ColorAdapter();
        rv_colors.setAdapter(colorAdapter);
        rv_colors.setLayoutManager(new GridLayoutManager(this.getContext(),4));

    }

}

