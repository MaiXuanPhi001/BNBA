package com.example.binance.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.binance.R;
import com.example.binance.model.Singleton;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Singleton singleton = Singleton.getInstance();
        System.out.println("userNameSign: " + singleton.getProfile().getEmail());
    }
}