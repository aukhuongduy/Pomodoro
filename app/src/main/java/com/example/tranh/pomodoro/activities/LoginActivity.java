package com.example.tranh.pomodoro.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.network.jsonmodels.LoginBodyJson;
import com.example.network.jsonmodels.LoginResponeJson;
import com.example.network.jsonmodels.RegisterBodyJson;
import com.example.network.jsonmodels.RegisterResponeJson;
import com.example.network.services.LoginService;
import com.example.network.services.RegisterService;
import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.settings.LoginCredentials;
import com.example.tranh.pomodoro.settings.SharedPrefs;
import com.google.gson.Gson;

import java.security.PrivateKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.toString();
    @BindView(R.id.et_username) EditText etUserName;
    @BindView(R.id.et_password) EditText etPassWord;
    @BindView(R.id.bt_register) Button btRegister;
    @BindView(R.id.bt_login) Button btLogin;
    private Retrofit retrofit;
    private String username;
    private String password;
    private String token;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skipLoginIsPossible();
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        ButterKnife.bind(this);

        etPassWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_DONE){
                    attemptLogin();
                    return false;
                }
                return false;
            }
        });
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
        Log.d(TAG, String.format("onCreate: %s", sharedPrefs.getLoginCredentials().toString()));


    }

    private void sendLogin(String username, String password) {
        //create base URL
        retrofit = new Retrofit.Builder().
                baseUrl("http://a-task.herokuapp.com/api/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        //Create Service
        LoginService loginService = retrofit.create(LoginService.class);
        //data
        MediaType jsonType = MediaType.parse("application/json");

        String loginJSON = (new Gson().toJson(new LoginBodyJson(username, password)));

        //
        RequestBody loginBody = RequestBody.create(jsonType, loginJSON);
        //format


        //Create Call

        Call<LoginResponeJson> loginCall =
                loginService.login(loginBody);

        loginCall.enqueue(new Callback<LoginResponeJson>() {
            @Override
            public void onResponse(Call<LoginResponeJson> call, Response<LoginResponeJson> response) {
                LoginResponeJson loginResponeJson = response.body();
                Log.d(TAG, "onResponse: "+ response.code());
                if (loginResponeJson == null) {
                    progressDialog.hide();
                    progressDialog.dismiss();
                    if (response.code() == 401) {
                        if (Integer.parseInt(response.headers().value(4)) > 60) {
                            Toast.makeText(LoginActivity.this, "Username or password is not correct", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "This username doesn't exits ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Something wrong here", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (response.code() == 200) {

                        progressDialog.hide();
                        progressDialog.dismiss();
                        token = SharedPrefs.getInstance().getAccessToken();
                        Log.d(TAG, String.format("onResponse: %s", token));
                        onLoginSuccess();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponeJson> call, Throwable t) {
                progressDialog.hide();
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Can't connect to server :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendRegister() {
        retrofit = new Retrofit.Builder().
                baseUrl("http://a-task.herokuapp.com/api/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        RegisterService registerService = retrofit.create(RegisterService.class);
        //data
        MediaType jsonType = MediaType.parse("application/json");

        String registerJSON = (new Gson().toJson(new RegisterBodyJson(username, password)));

        RequestBody registerBody = RequestBody.create(jsonType, registerJSON);

        registerService.register(registerBody).enqueue(new Callback<RegisterResponeJson>() {
            @Override
            public void onResponse(Call<RegisterResponeJson> call, Response<RegisterResponeJson> response) {
                progressDialog.hide();
                progressDialog.dismiss();
                RegisterResponeJson registerResponeJson = response.body();
                if (registerResponeJson == null) {
                    Log.d(TAG, String.format("onResponse: %s", response.headers().value(4)));
                    if (response.code() == 400) {
                        Toast.makeText(LoginActivity.this, "This account already exits", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Some thing wrong with system", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (response.code() == 200) {
                        Toast.makeText(LoginActivity.this, String.format("Registered"), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponeJson> call, Throwable t) {
                progressDialog.hide();
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Can't connect to server :(", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void onLoginSuccess() {
        SharedPrefs.getInstance().put(new LoginCredentials(username, password,token, false));
        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
//
        goToActivity();
    }


    private void skipLoginIsPossible() {
        if (SharedPrefs.getInstance().getAccessToken() != null) goToActivity();
    }


    private void attemptRegister() {
        username = etUserName.getText().toString();
        password = etPassWord.getText().toString();
        sendRegister();
        getLoadingScreen();
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
        sendLogin(username, password);
        getLoadingScreen();


//        if (username.equals("admin") && password.equals("admin")) {
//            //Notifiaction
//
//        } else {
//            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
//        }
    }

    private void getLoadingScreen() {
        progressDialog.setMessage("Logging...");
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.show();

    }

    private void goToActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
