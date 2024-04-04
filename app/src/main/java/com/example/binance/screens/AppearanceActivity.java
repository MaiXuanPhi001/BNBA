package com.example.binance.screens;

import static com.example.binance.constants.Storages.APPEARANCE;
import static com.example.binance.constants.Storages.DARK;
import static com.example.binance.constants.Storages.DARK_INT;
import static com.example.binance.constants.Storages.DEFAULT_LANGUAGE;
import static com.example.binance.constants.Storages.LANGUAGE;
import static com.example.binance.constants.Storages.LIGHT;
import static com.example.binance.constants.Storages.SETTING;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.binance.R;

public class AppearanceActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayoutLightMode, constraintLayoutDarktMode;
    private TextView txtCheckLightMode, txtCheckDarkMode;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appearance);

        init();
        constraintLayoutLightModeOnclick();
        constraintLayoutDarktModeOnclick();
        imgBackOnclick();
        loadAppearance();
    }

    private void imgBackOnclick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void loadAppearance() {
        int appearance = AppCompatDelegate.getDefaultNightMode();
        if (appearance == DARK_INT) {
            txtCheckDarkMode.setVisibility(View.VISIBLE);
            txtCheckLightMode.setVisibility(View.INVISIBLE);
        } else {
            txtCheckLightMode.setVisibility(View.VISIBLE);
            txtCheckDarkMode.setVisibility(View.INVISIBLE);
        }
    }
    private void constraintLayoutDarktModeOnclick() {
        constraintLayoutDarktMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                setAppearanceSharedPreferences(DARK);
            }
        });
    }
    private void constraintLayoutLightModeOnclick() {
        constraintLayoutLightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                setAppearanceSharedPreferences(LIGHT);
            }
        });
    }
    private void setAppearanceSharedPreferences(String appearance) {
        SharedPreferences.Editor editor = getSharedPreferences(SETTING, MODE_PRIVATE).edit();
        editor.putString(APPEARANCE, appearance);
        editor.apply();
    }
    private void init() {
        imgBack = findViewById(R.id.image_back_appearance);
        txtCheckDarkMode = findViewById(R.id.text_check_dark_mode_appearance);
        txtCheckLightMode = findViewById(R.id.text_check_light_mode_appearance);
        constraintLayoutDarktMode = findViewById(R.id.constraint_layout_dark_mode_appearance);
        constraintLayoutLightMode = findViewById(R.id.constraint_layout_light_mode_appearance);
    }
}