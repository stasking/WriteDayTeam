package com.annex.writeday.sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.annex.writeday.MyApplication;
import com.annex.writeday.R;
import com.annex.writeday.apiservices.service.APIService;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    //private static final String TAG = "SignupActivity";
    private static final String USERNAME_EXIST = "USERNAME_EXIST";
    private static final String SIGNUP_SUCCESS = "SUCCESS";

    @Bind(R.id.input_nickname) EditText _usernameText;
    @Bind(R.id.input_username) EditText _nicknameText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed(0);
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Регистрация...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String nickname = _nicknameText.getText().toString();
        String password = _passwordText.getText().toString();
        String key = "2016";

        final MyApplication mApplication = (MyApplication)getApplicationContext();
        APIService service = mApplication.getRestClient();

        Call<ResponseBody> result = service.createUser(username, password, nickname, key);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try
                {
                    String responseBody = response.body().string();
                    JSONObject obj = new JSONObject(responseBody);
                    if (response.isSuccessful())
                    {
                        String result = obj.getString(mApplication.getTagResult());
                        switch (result)
                        {
                            case USERNAME_EXIST: {
                                onSignupFailed(1);
                                break;
                            }
                            case SIGNUP_SUCCESS: {
                                onSignupSuccess();
                                break;
                            }
                            default: {
                                onSignupFailed(2);
                                break;
                            }
                        }
                    }
                    else
                    {
                        onSignupFailed(2);
                    }
                }
                catch (Exception e)
                {
                    onSignupFailed(3);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                onSignupFailed(3);
                t.printStackTrace();
            }
        });

    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);

        Intent intent = new Intent();
        intent.putExtra("RESULT_LOGIN", _usernameText.getText().toString());
        intent.putExtra("RESULT_PASSWORD", _passwordText.getText().toString());
        setResult(RESULT_OK, intent);

        finish();
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void onSignupFailed(int typeError) {

        if(typeError == 1) {
            _usernameText.setError("Данный логин занят.");
        }
        else if(typeError == 2) {
            Toast.makeText(getBaseContext(), "Неверный логин или пароль.", Toast.LENGTH_LONG).show();
        }
        else if(typeError == 3) {
            Toast.makeText(getBaseContext(), "Произошла ошибка. Проверьте Ваше подключение к интернету.", Toast.LENGTH_LONG).show();
        }

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String nickname = _nicknameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty() || username.length() < 3) {
            _usernameText.setError("Слишком короткий логин.");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (nickname.isEmpty() || /*!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()*/nickname.length() < 4) {
            _nicknameText.setError("Слишком короткое имя.");
            valid = false;
        } else {
            _nicknameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 32) {
            _passwordText.setError("Пароль должен содержать не менее 6 символов.");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}