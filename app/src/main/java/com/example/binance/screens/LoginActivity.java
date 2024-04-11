package com.example.binance.screens;

import static com.example.binance.constants.Storages.SETTING;
import static com.example.binance.constants.Storages.TOKEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binance.R;
import com.example.binance.models.users.Profile;
import com.example.binance.models.logins.RequestLogin;
import com.example.binance.models.logins.ResponseLogin;
import com.example.binance.models.Singleton;
import com.example.binance.services.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private ImageView imgPassword;
    private AppCompatButton btnLogin;
    private TextView txtCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        imgPasswordOnClick();
        txtCreateAccountOnClick();
        btnLoginOnclick();
    }

    private void btnLoginOnclick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAPILogin();
            }
        });
    }

    private void callAPILogin() {
        btnLogin.setEnabled(false);
        btnLogin.setText("...");
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        RequestLogin requestLogin = new RequestLogin(email, password);
        UserService.userService.login(requestLogin).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                setBtnLogin();
                // Hàm trả về ResponseLogin có thể khai báo thiếu field nhưng phải đúng tên và kiểu dữ liệu, nếu không đúng sẽ chạy hàm onFailure bên dưới
                // Vd: response có trả về filed message nhưng không nhất thiết phải khai báo field message trong class ResponseLogin
                // Vd: balance trong response là kiểu float thì phải khai báo đúng kiểu float, nếu không đúng sẽ chạy hàm onFailure bên dưới
                if (response.code() == 200) {
                    Profile profile = response.body().getData();
                    Singleton singleton = Singleton.getInstance();
                    singleton.setProfile(profile);
                    singleton.setLogin(true);
                    SharedPreferences.Editor editor = getSharedPreferences(SETTING, MODE_PRIVATE).edit();
                    editor.putString(TOKEN, profile.getToken());
                    editor.apply();
                    finish();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String errorMessage = jsonObject.getString("message");
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                setBtnLogin();
                Toast.makeText(LoginActivity.this, R.string.Cannot_connect_server, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setBtnLogin() {
        btnLogin.setEnabled(true);
        btnLogin.setText(R.string.login);
    }

    private void imgPasswordOnClick() {
        imgPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int inputType = edtPassword.getInputType();
                if (inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD + 1) {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    imgPassword.setImageResource(R.drawable.view);
                } else {
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD + 1);
                    imgPassword.setImageResource(R.drawable.hidden);
                }
                edtPassword.setSelection(edtPassword.getText().length());
            }
        });
    }

    private void txtCreateAccountOnClick() {
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void init () {
        btnLogin = findViewById(R.id.btn_login);
        edtEmail = findViewById(R.id.edt_email_login);
        edtPassword = findViewById(R.id.edt_password_login);
        imgPassword = findViewById(R.id.img_password_login);
        txtCreateAccount = findViewById(R.id.txt_create_account_login);
    }
}