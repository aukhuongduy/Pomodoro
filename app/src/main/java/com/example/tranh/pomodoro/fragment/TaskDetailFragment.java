package com.example.tranh.pomodoro.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.adapters.ColorAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {

    @BindView(R.id.rv_colors)
    RecyclerView rv_colors;
    @BindView(R.id.et_payment_per_hour)
    EditText et_payment_per_hour;


    public TaskDetailFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        setUI(view);
        return view;
    }

    private void setUI(View view) {
        ButterKnife.bind(this,view);
        ColorAdapter colorAdapter = new ColorAdapter();
        rv_colors.setAdapter(colorAdapter);
        rv_colors.setLayoutManager(new GridLayoutManager(this.getContext(),4));
        et_payment_per_hour.setText("0.0");
        et_payment_per_hour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(et_payment_per_hour.getText()==null){
                    et_payment_per_hour.setHint("");
                }
            }
        });
        AppCompatActivity activity =  (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Create new task");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_task,menu);
    }
}
