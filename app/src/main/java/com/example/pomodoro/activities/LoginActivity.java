package com.example.pomodoro.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pomodoro.network.AccountNetworkContext;
import com.example.pomodoro.network.jsonmodels.LoginBodyJson;
import com.example.pomodoro.network.jsonmodels.LoginResponeJson;
import com.example.pomodoro.network.jsonmodels.RegisterBodyJson;
import com.example.pomodoro.network.jsonmodels.RegisterResponeJson;
import com.example.pomodoro.network.services.LoginService;
import com.example.pomodoro.network.services.RegisterService;
import com.example.pomodoro.settings.LoginCredentials;
import com.example.pomodoro.settings.SharedPrefs;
import com.example.tranh.pomodoro.R;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_username)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassWord;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.text_input_layout)
    TextInputLayout tilUsername;
    @BindView(R.id.text_input_layout2)
    TextInputLayout tilPassword;
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

        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etUserName.getText().length() <= 0) {
                    tilUsername.setError("Username cannot be blank!");
                } else {
                    tilUsername.setError(null);
                }
            }
        });
        etPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPrefs.getInstance().clear();
                if (etPassWord.getText().length() <= 0) {
                    tilPassword.setError("Your password can not be blank");
                } else if (etPassWord.getText().length() < 5) {
                    tilPassword.setError("Your passsword must be longer 5 character");
                } else {
                    tilPassword.setError(null);
                }
            }
        });

        etPassWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
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

    }

    private void sendLogin(String username, String password) {

        //Create Service
        LoginService loginService = AccountNetworkContext.instance.getRetrofit().create(LoginService.class);
        //data
        MediaType jsonType = MediaType.parse("application/json");

        String loginJSON = (new Gson().toJson(new LoginBodyJson(username, password)));


        RequestBody loginBody = RequestBody.create(jsonType, loginJSON);
        //format

        //Create Call

        Call<LoginResponeJson> loginCall =
                loginService.login(loginBody);

        loginCall.enqueue(new Callback<LoginResponeJson>() {
            @Override
            public void onResponse(Call<LoginResponeJson> call, Response<LoginResponeJson> response) {
                LoginResponeJson loginResponeJson = response.body();
                Log.d(TAG, "onResponse: " + response.code());
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
                        Log.d(TAG, "onResponse: ok vao");
                        progressDialog.hide();
                        progressDialog.dismiss();
                        token = loginResponeJson.getAccessToken();
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
        RegisterService registerService = AccountNetworkContext.instance.getRetrofit().create(RegisterService.class);
        //data
        MediaType jsonType = MediaType.parse("application/json");

        String registerJSON = (new Gson().toJson(new RegisterBodyJson(username, password)));
        Log.d(TAG, "sendRegister: "+registerJSON);

        RequestBody registerBody = RequestBody.create(jsonType, registerJSON);

        Call<RegisterResponeJson> registerCall = registerService.register(registerBody);

        registerCall.enqueue(new Callback<RegisterResponeJson>() {
            @Override
            public void onResponse(Call<RegisterResponeJson> call, Response<RegisterResponeJson> response) {
                progressDialog.hide();
                progressDialog.dismiss();
                Log.d(TAG, String.format("onResponse: " + response.code()));
                if (response.code() == 400) {
                    Toast.makeText(LoginActivity.this, "This account already exits", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 200) {
                    Toast.makeText(LoginActivity.this, String.format("Registered"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Some thing wrong with system", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RegisterResponeJson> call, Throwable t) {
                progressDialog.hide();
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Can't connect to server :(", Toast.LENGTH_SHORT).show();

            }
        });

//        registerService.register(registerBody).enqueue(new Callback<RegisterResponeJson>() {
//            @Override
//            public void onResponse(Call<RegisterResponeJson> call, Response<RegisterResponeJson> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponeJson> call, Throwable t) {
//
//            }
//        });
    }

    private void onLoginSuccess() {
        SharedPrefs.getInstance().put(new LoginCredentials(username, password, token, false));
        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
        goToActivity();
    }


    private void skipLoginIsPossible() {
        if (SharedPrefs.getInstance().getAccessToken() != null) goToActivity();
    }


    private void attemptRegister() {
        if (isAcceptLogin()) {
            username = etUserName.getText().toString();
            password = etPassWord.getText().toString();
            sendRegister();
            getLoadingScreen();
        } else {
            setError();
        }

    }

    private void setError() {
        if (etPassWord.getText().length() <= 0) {
            tilPassword.setError("Your password can not be blank");
        } else if (etPassWord.getText().length() < 5) {
            tilPassword.setError("Your passsword must be longer 5 character");
        } else {
            tilPassword.setError(null);
        }

        if (etUserName.getText().length() <= 0) {
            tilUsername.setError("Username cannot be blank!");
        } else {
            tilUsername.setError(null);
        }
    }


    private boolean isAcceptLogin() {
        if (etUserName.getText().length() > 0 && etPassWord.getText().length() >= 5) {
            return true;
        } else {
            return false;
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
        if (isAcceptLogin()) {
            username = String.valueOf(etUserName.getText());
            password = etPassWord.getText().toString();
            sendLogin(username, password);
            Log.d(TAG, "attemptLogin: " + token);
            getLoadingScreen();
        } else {
            setError();
        }


    }

    private void getLoadingScreen() {
        progressDialog.setMessage("Logging...");
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.show();

    }

    private void goToActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
