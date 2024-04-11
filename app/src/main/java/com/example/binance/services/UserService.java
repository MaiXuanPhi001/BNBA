package com.example.binance.services;

import static com.example.binance.constants.Service.DOMAIN;
import static com.example.binance.constants.Service.GET_PROFILE;
import static com.example.binance.constants.Service.LOGIN;

import com.example.binance.models.logins.RequestLogin;
import com.example.binance.models.logins.ResponseLogin;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    UserService userService = new Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService.class);

    @POST(LOGIN)
    Call<ResponseLogin> login(@Body RequestLogin requestLogin);

    @POST(GET_PROFILE)
    Call<ResponseLogin> getProfile(@Header("Authorization") String bearserToken);

//    config.headers = {
//        Authorization: `Bearer ${token}`,
//        Accept: 'application/json',
//                'Content-Type': 'application/json',
//    };
}
