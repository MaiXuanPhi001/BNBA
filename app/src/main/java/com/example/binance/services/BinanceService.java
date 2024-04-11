package com.example.binance.services;

import static com.example.binance.constants.Service.DOMAIN;
import static com.example.binance.constants.Service.GET_CHART;
import static com.example.binance.constants.Service.GET_LIST_COIN;

import com.example.binance.models.getChart.RequestGetChart;
import com.example.binance.models.getChart.ResponseGetChart;
import com.example.binance.models.logins.RequestLogin;
import com.example.binance.models.coins.ResponseGetListCoin;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BinanceService {
    BinanceService binanceService = new Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BinanceService.class);

    @POST(GET_LIST_COIN)
    Call<ResponseGetListCoin> getProfile(@Header("Authorization") String bearerToken);

    @POST(GET_CHART)
    Call<ResponseGetChart> getChart(
            @Header("Authorization") String bearerToken,
            @Body RequestGetChart requestGetChart
    );
}
