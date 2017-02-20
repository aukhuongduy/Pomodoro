package com.example.khuongduy.pomodoro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.network.NetworkContext;
import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.database.models.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
