package com.example.binance.test;

import static com.example.binance.constants.Service.DOMAIN;
import static com.example.binance.constants.Service.FUNDING;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @GET(FUNDING)
    Call<UserTest> convertToVND(@Query("access_key") String access_key,
                                @Query("currency") String currency,
                                @Query("source") String source,
                                @Query("format") int format);

    @GET(FUNDING)
    Call<ResponseBase> funding();
}
