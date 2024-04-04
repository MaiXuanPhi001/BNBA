package com.example.binance.utils;

import static com.example.binance.constants.Storages.DEFAULT_LANGUAGE;
import static com.example.binance.constants.Storages.LANGUAGE;
import static com.example.binance.constants.Storages.SETTING;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

public class Utils {
    // Ẩn thanh điều hướng android
    public void setSystemUIVisibility(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
    }

    public void hideNavigation(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    public String getLanguageSharedPreferences(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        String language = sharedPreferences.getString(LANGUAGE, DEFAULT_LANGUAGE);
        return language;
    }

    public void checkLanguage(Activity activity, String language) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        String currentLanguage = sharedPreferences.getString(LANGUAGE, DEFAULT_LANGUAGE);

        if (!currentLanguage.equals(language)) {
            activity.recreate();
        }
    }
}
