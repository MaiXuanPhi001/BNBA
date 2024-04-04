package com.example.binance.screens;

import static com.example.binance.constants.Storages.DEFAULT_LANGUAGE;
import static com.example.binance.constants.Storages.LANGUAGE;
import static com.example.binance.constants.Storages.SETTING;
import static com.example.binance.constants.Storages.VIETNAMESE_LANGUAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binance.R;
import com.example.binance.utils.Utils;

public class SettingActivity extends AppCompatActivity {
    private String language = "";
    private Utils utils = new Utils();
    private TextView txtValueLanguage;
    private ImageView imageBackSetting;
    private ConstraintLayout constraintLayoutLanguageSetting, constraintLayoutAppearanceSetting;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
        language = utils.getLanguageSharedPreferences(SettingActivity.this);
        setTxtValueLanguage();

        imageBackSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        constraintLayoutLanguageSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, LanguageActivity.class);
                startActivity(intent);
            }
        });

        constraintLayoutAppearanceSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, AppearanceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setTxtValueLanguage() {
        if (language.equals(VIETNAMESE_LANGUAGE)) {
            txtValueLanguage.setText(R.string.Vietnamese);
        } else {
            txtValueLanguage.setText(R.string.English);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        utils.checkLanguage(SettingActivity.this, language);
    }

    private void init() {
        imageBackSetting = findViewById(R.id.image_back_settings);
        txtValueLanguage = findViewById(R.id.txt_value_language_setting);
        constraintLayoutLanguageSetting = findViewById(R.id.constaint_layout_language_setting);
        constraintLayoutAppearanceSetting = findViewById(R.id.constaint_layout_appearance_setting);
    }
}