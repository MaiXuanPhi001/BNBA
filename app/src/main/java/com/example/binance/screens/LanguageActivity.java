package com.example.binance.screens;

import static com.example.binance.constants.Storages.DEFAULT_LANGUAGE;
import static com.example.binance.constants.Storages.LANGUAGE;
import static com.example.binance.constants.Storages.SETTING;
import static com.example.binance.constants.Storages.VIETNAMESE_LANGUAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binance.R;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {
    ImageView imageBack;
    TextView textCheckEnglish, textCheckVietnamese;
    ConstraintLayout constraintLayoutEnglish, constraintLayoutVietnamese;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        init();
        loadLanguage();
        imageBackClick();
        constraintLayoutEnglishClick();
        constraintLayoutVietnameseClick();
    }

    private void constraintLayoutEnglishClick() {
        constraintLayoutEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage(DEFAULT_LANGUAGE);
            }
        });
    }

    private void constraintLayoutVietnameseClick() {
        constraintLayoutVietnamese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage(VIETNAMESE_LANGUAGE);
            }
        });
    }

    private void imageBackClick() {
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        recreate();
    }

    private void loadLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        String language = sharedPreferences.getString(LANGUAGE, DEFAULT_LANGUAGE);

        if (language.equals(VIETNAMESE_LANGUAGE)) {
            textCheckEnglish.setVisibility(View.INVISIBLE);
            textCheckVietnamese.setVisibility(View.VISIBLE);
        } else {
            textCheckVietnamese.setVisibility(View.INVISIBLE);
            textCheckEnglish.setVisibility(View.VISIBLE);
        }
    }
    private void init() {
        textCheckEnglish = findViewById(R.id.text_check_english_language);
        textCheckVietnamese = findViewById(R.id.text_check_vietnamese_language);
        imageBack = findViewById(R.id.image_back_language);
        constraintLayoutEnglish = findViewById(R.id.constraint_layout_english_language);
        constraintLayoutVietnamese = findViewById(R.id.constraint_layout_vietnamese_language);
    }
}