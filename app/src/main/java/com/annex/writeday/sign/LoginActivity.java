package com.annex.writeday.sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.annex.writeday.MainActivity;
import com.annex.writeday.MyApplication;
import com.annex.writeday.R;
import com.annex.writeday.apiservices.PersistentCookieStore;
import com.annex.writeday.apiservices.service.APIService;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private static final String LOGIN_SUCCESS = "OK";

    @Bind(R.id.input_username) EditText _usernameText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed(0);
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Авторизация...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        String rememberMe = "On";

        final MyApplication mApplication = (MyApplication)getApplicationContext();
        final APIService service = mApplication.getRestClient();

        //Call<ResponseBody> result = service.loginUser(username, password, rememberMe);
        Call<ResponseBody> result = service.loginUser(username, password);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();

                Log.e("Result", "" + response.code());
                
                try
                {
                    String responseBody = response.body().string();
                    JSONObject obj = new JSONObject(responseBody);
                    String result = obj.getString(mApplication.getTagResult());

                    if (response.isSuccessful())
                    {
                        switch (result)
                        {
                            case LOGIN_SUCCESS: {

                                //Log.e("Set-Cookie: ", response.headers().toString());

                                //Log.e("Cookie: ", response.headers().get("Cookie"));

                                PersistentCookieStore persistentCookieStore = new PersistentCookieStore(getApplicationContext());
                                String cooks = persistentCookieStore.getCookies().toString();
                                Log.e("Cookie: ", cooks);

                                onLoginSuccess();
                                break;
                            }
                            default: {
                                onLoginFailed(1);
                                break;
                            }
                        }
                        _loginButton.setEnabled(true);
                    }
                    else
                    {
                        onLoginFailed(1);
                        Log.e("Failed1 ", "1");
                    }
                }
                catch (Exception e)
                {
                    onLoginFailed(1);
                    Log.e("Failed 2", "" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                onLoginFailed(2);
                Log.e("Failed 3", "" + t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                //this.finish();

                final String username = data.getStringExtra("RESULT_LOGIN");
                final String password = data.getStringExtra("RESULT_PASSWORD");

                this._usernameText.setText(username);
                this._passwordText.setText(password);

                this._loginButton.performClick();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {



        _loginButton.setEnabled(true);
        finish();
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        main.putExtra("NEW_USER", "YES");
        startActivity(main);
    }

    public void onLoginFailed(int typeError) {

        if(typeError == 1) {
            Toast.makeText(getBaseContext(), "Введен неверный логин или пароль.", Toast.LENGTH_LONG).show();
        }
        else if(typeError == 2) {
            Toast.makeText(getBaseContext(), "Произошла ошибка. Проверьте Ваше подключение к интернету.", Toast.LENGTH_LONG).show();
        }
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty() || username.length() < 4) {
            _usernameText.setError("Неверный формат логина.");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            _passwordText.setError("Не менее 6 символов.");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}