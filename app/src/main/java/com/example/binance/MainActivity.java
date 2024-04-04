package com.example.binance;

import static com.example.binance.constants.Service.BEARER;
import static com.example.binance.constants.Storages.APPEARANCE;
import static com.example.binance.constants.Storages.DARK;
import static com.example.binance.constants.Storages.DARK_INT;
import static com.example.binance.constants.Storages.DEFAULT_LANGUAGE;
import static com.example.binance.constants.Storages.LANGUAGE;
import static com.example.binance.constants.Storages.LIGHT;
import static com.example.binance.constants.Storages.LIGHT_INT;
import static com.example.binance.constants.Storages.SETTING;
import static com.example.binance.constants.Storages.TOKEN;
import static com.example.binance.constants.Storages.VIETNAMESE_LANGUAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.binance.model.Profile;
import com.example.binance.model.ResponseLogin;
import com.example.binance.model.Singleton;
import com.example.binance.screens.BottomTabScreen;
import com.example.binance.services.UserService;
import com.example.binance.test.TestActivity;
import com.example.binance.utils.Utils;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("abc");

        sharedPreferences = getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        new Utils().setSystemUIVisibility(MainActivity.this);
        checkDarkLight();
    }

    private void checkDarkLight () {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        String appearance = sharedPreferences.getString(APPEARANCE, LIGHT);
        int nightMode = AppCompatDelegate.getDefaultNightMode();

        if (appearance.equals(LIGHT)) {
            if (nightMode == LIGHT_INT) {
                handlerDelay();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        } else {
            if (nightMode == DARK_INT) {
                handlerDelay();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
    }

    private void handlerDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadLanguage();
                checkToken();
            }
        }, 2000);
    }

    private void checkToken() {
        String token = sharedPreferences.getString(TOKEN, "");
        if (!token.equals("")) {
            UserService.userService.getProfile(BEARER + " " + token).enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    if (response.code() == 200) {
                        Profile profile = response.body().getData();
                        Singleton singleton = Singleton.getInstance();
                        singleton.setProfile(profile);
                        singleton.setLogin(true);
                    }
                    moveHomeScreen();
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    moveHomeScreen();
                }
            });
        } else {
            moveHomeScreen();
        }
    }

    private void moveHomeScreen() {
        Intent intent = new Intent(MainActivity.this, BottomTabScreen.class);
        startActivity(intent);
        finish();
    }

    private void loadLanguage() {
        String language = sharedPreferences.getString(LANGUAGE, DEFAULT_LANGUAGE);
        if (language.equals(VIETNAMESE_LANGUAGE)) {
            setLanguage(VIETNAMESE_LANGUAGE);
        } else {
            setLanguage(DEFAULT_LANGUAGE);
        }
    }

    private void setLanguage(String lng) {
        Locale locale = new Locale(lng);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences(SETTING, MODE_PRIVATE).edit();
        editor.putString(LANGUAGE, lng);
        editor.apply();
    }
}