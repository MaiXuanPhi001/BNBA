package com.example.binance.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.binance.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        JobTest job = new JobTest(1, "coder");
        List<FavoriteTest> favorites = new ArrayList<>();
        favorites.add(new FavoriteTest(1, "Chess"));
        favorites.add(new FavoriteTest(2, "Rubik"));

        UserTest user = new UserTest(1, "phi", true, job, favorites);
        Gson gson = new Gson();
        String strJson = gson.toJson(user);
        Log.e("String JSON: ", strJson);

//        callAPITest();
        callAPIFunding();
    }

    private void callAPITest() {
        ApiService.apiService.convertToVND("kdjfd", "VND", "USD", 1).enqueue(new Callback<UserTest>() {
            @Override
            public void onResponse(Call<UserTest> call, Response<UserTest> response) {
                UserTest user = response.body();
                System.out.println(user.getId());
            }

            @Override
            public void onFailure(Call<UserTest> call, Throwable t) {
                Toast.makeText(TestActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callAPIFunding() {
        ApiService.apiService.funding().enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                ResponseBase responseBase = response.body();
                System.out.println("res: " + responseBase.getMessage());
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                Toast.makeText(TestActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}