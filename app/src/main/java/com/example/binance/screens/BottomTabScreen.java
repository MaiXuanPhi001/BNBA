package com.example.binance.screens;

import static com.example.binance.constants.Storages.APPEARANCE;
import static com.example.binance.constants.Storages.DARK;
import static com.example.binance.constants.Storages.DARK_INT;
import static com.example.binance.constants.Storages.LIGHT;
import static com.example.binance.constants.Storages.SETTING;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.binance.MainActivity;
import com.example.binance.R;
import com.example.binance.fragments.HomeFragment;
import com.example.binance.fragments.MarketsFragment;
import com.example.binance.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class BottomTabScreen extends AppCompatActivity {
    private int appearance;
    private BottomNavigationView navigationView;
    private String language = "";
    private Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_screen);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom_tab, new HomeFragment())
//                .addToBackStack(null)
                .commit();

        navigationView = findViewById(R.id.bottom_nav);

        language = utils.getLanguageSharedPreferences(BottomTabScreen.this);
        navigationView.setItemIconTintList(null);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.tab_home) {
                    fragment = new HomeFragment();
                }
                if (item.getItemId() == R.id.tab_market) {
                    fragment = new MarketsFragment();
                }
                if (item.getItemId() == R.id.tab_copy_trade) {
                    Toast.makeText(BottomTabScreen.this, "tab_copy_trade", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.tab_futures) {
                    Toast.makeText(BottomTabScreen.this, "tab_futures", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.tab_wallets) {
                    Toast.makeText(BottomTabScreen.this, "tab_wallets", Toast.LENGTH_SHORT).show();
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom_tab, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        utils.checkLanguage(BottomTabScreen.this, language);
        appearance = AppCompatDelegate.getDefaultNightMode();
        if (appearance == DARK_INT) {
            navigationView.setBackgroundResource(R.color.black2);
        } else {
            navigationView.setBackgroundResource(R.color.white);
        }
    }
}