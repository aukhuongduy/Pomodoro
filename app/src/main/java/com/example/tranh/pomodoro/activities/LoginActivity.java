package com.example.tranh.pomodoro.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.network.jsonmodels.LoginBodyJson;
import com.example.network.jsonmodels.LoginResponeJson;
import com.example.network.services.LoginService;
import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.settings.LoginCredentials;
import com.example.tranh.pomodoro.settings.SharedPrefs;
import com.google.gson.Gson;

import java.security.PrivateKey;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private  static final String TAG = LoginActivity.class.toString();
    private EditText etUserName;
    private EditText etPassWord;
    private Button btRegister;
    private Button btLogin;
    private Retrofit retrofit;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        skipLoginIsPossible();
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
    private void sendLogin(String username, String password){
        retrofit = new Retrofit.Builder().
                baseUrl("http://a-task.herokuapp.com/api/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        LoginService loginService = retrofit.create(LoginService.class);
        //data
        MediaType jsonType = MediaType.parse("application/json");

        String loginJSON =(new Gson().toJson(new LoginBodyJson(username,password)));

        RequestBody loginBody = RequestBody.create(jsonType,loginJSON);
        //format


        loginService.login(loginBody).enqueue(new Callback<LoginResponeJson>() {
            @Override
            public void onResponse(Call<LoginResponeJson> call, Response<LoginResponeJson> response) {
                Log.d(TAG, "onResponse: ");
                LoginResponeJson loginResponeJson = response.body();
                if(loginResponeJson==null){
                    Log.d(TAG, "onResponse: Could not parse body");
                }else{
                    Log.d(TAG, "onResponse: Oh yeah !!!!!!!! ");
                    if(response.code()==200){
                        onLoginSuccess();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponeJson> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
    private void onLoginSuccess(){
        SharedPrefs.getInstance().put(new LoginCredentials(username,password,false));
            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
//
            goToActivity();
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
        username = String.valueOf(etUserName.getText());
         password = etPassWord.getText().toString();
        sendLogin(username,password);



//        if (username.equals("admin") && password.equals("admin")) {
//            //Notifiaction
//
//        } else {
//            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
//        }
    }

    private void goToActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }
}
