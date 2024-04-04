package com.example.binance.services;

import static com.example.binance.constants.Service.DOMAIN;
import static com.example.binance.constants.Service.GET_LIST_COIN;
import static com.example.binance.constants.Service.GET_PROFILE;

import com.example.binance.model.ResponseGetListCoin;
import com.example.binance.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
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
}
