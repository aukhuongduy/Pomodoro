package com.example.tranh.pomodoro.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.settings.LoginCredentials;
import com.example.tranh.pomodoro.settings.SharedPrefs;
import com.google.gson.Gson;

import java.security.PrivateKey;

public class LoginActivity extends AppCompatActivity {

    private  static final String TAG = LoginActivity.class.toString();
    private EditText etUserName;
    private EditText etPassWord;
    private Button btRegister;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skipLoginIsPossible();
        setContentView(R.layout.activity_login);
        etUserName = (EditText) this.findViewById(R.id.et_username);
        etPassWord = (EditText) this.findViewById(R.id.et_password);
        btLogin = (Button) this.findViewById(R.id.bt_login);
        btRegister = (Button) this.findViewById(R.id.bt_register);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
        SharedPrefs sharedPrefs = new SharedPrefs(this);
        sharedPrefs.put(new LoginCredentials("admin","admin",false));
        Log.d(TAG, String.format("onCreate: %s", sharedPrefs.getLoginCredentials().toString()));

    }

    private void skipLoginIsPossible() {
        if(SharedPrefs.getInstance().getLoginCredentials() != null )goToActivity();
    }


    private void attemptRegister() {
        String userName = etUserName.getText().toString();
        String passWord = etPassWord.getText().toString();
        if (!checkSpecialCharacter(userName)) {
            Toast.makeText(this, "Not support special character!", Toast.LENGTH_SHORT).show();
        }
        if (passWord.length() < 8) {
            Toast.makeText(this, "Password more than 8 characters. Please try again!", Toast.LENGTH_SHORT).show();
        } else if (userName.equals("admin") || userName.isEmpty() || passWord.isEmpty()) {
            //Notifiaction
            Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkSpecialCharacter(String s) {
        for (int i = 0; i < s.length(); i++) {
            if ((!Character.isLetter(s.charAt(i)) && !Character.isDigit(s.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    private void attemptLogin() {
        String username = String.valueOf(etUserName.getText());
        String password = etPassWord.getText().toString();


        if (username.equals("admin") && password.equals("admin")) {
            //Notifiaction
            SharedPrefs.getInstance().put(new LoginCredentials(username,password,false));
            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();

            goToActivity();
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }
}
