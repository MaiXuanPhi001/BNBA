package com.example.binance.fragments;

import static com.example.binance.constants.Service.BEARER;
import static com.example.binance.constants.Service.DOMAIN;
import static com.example.binance.constants.Storages.DARK_INT;
import static com.example.binance.constants.Storages.SETTING;
import static com.example.binance.constants.Storages.TOKEN;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.binance.R;
import com.example.binance.adapter.CoinHomeAdapter;
import com.example.binance.models.coins.Coin;
import com.example.binance.models.coins.ResponseGetListCoin;
import com.example.binance.models.Singleton;
import com.example.binance.screens.LoginActivity;
import com.example.binance.screens.ProfileActivity;
import com.example.binance.screens.SignUpActivity;
import com.example.binance.services.BinanceService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private LinearLayout linearLayoutSearch;
    private CardView cvLoginOrSignUp;
    private ImageView imageProfile;
    private int appearance;
    private Button btnLogin, btnSignUp;
    private SharedPreferences sharedPreferences;
    private RecyclerView rcvListCoin;
    private CoinHomeAdapter coinHomeAdapter;
    private Socket socket;

    {
        try {
            socket = IO.socket(DOMAIN);
        } catch (URISyntaxException e) {
            System.out.println("error socket: " + e.getMessage());
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init(view);
        setAdapter();
        imageProfileOnClick();
        btnLoginOnClick();
        btnSignUpOnClick();
        connectSocket();
        return view;
    }

    private void setAdapter() {
        // get width screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        coinHomeAdapter = new CoinHomeAdapter(getContext(), width);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcvListCoin.setLayoutManager(linearLayoutManager);
        rcvListCoin.setAdapter(coinHomeAdapter);
    }
    private void connectSocket() {
        socket.on("listCoin", onListCoin);
        socket.connect();
    }

    private Emitter.Listener onListCoin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (getActivity() == null) {
                return;
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Singleton singleton = Singleton.getInstance();
                    boolean isLogin = singleton.isLogin();
                    if (isLogin) {
                        JSONArray data = (JSONArray) args[0];
                        Type listType = new TypeToken<List<Coin>>(){}.getType();

                        // Use Gson to convert JSON array to ArrayList
                        List<Coin> coins = new Gson().fromJson(data.toString(), listType);
                        coinHomeAdapter.setData(coins);
                    }
                }
            });
        }
    };
    private void callAPIGetListCoin() {
        String token = sharedPreferences.getString(TOKEN, "");
        boolean isLogin = Singleton.getInstance().isLogin();
        if (isLogin) {
            BinanceService.binanceService.getProfile(BEARER + " " + token).enqueue(new Callback<ResponseGetListCoin>() {
                @Override
                public void onResponse(Call<ResponseGetListCoin> call, Response<ResponseGetListCoin> response) {
                    if (response.code() == 200) {
                        coinHomeAdapter.setData(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<ResponseGetListCoin> call, Throwable t) {
                    System.out.println("Error: " + t.getMessage());
                }
            });
        } else {
            // set list view = null
        }
    }

    private void btnSignUpOnClick() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private void btnLoginOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void imageProfileOnClick() {
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setBackgroundSearch();
        checkLogin();
        callAPIGetListCoin();
    }

    private void setBackgroundSearch() {
        appearance = AppCompatDelegate.getDefaultNightMode();
        if (appearance == DARK_INT) {
            linearLayoutSearch.setBackgroundResource(R.drawable.background_search_home_dark);
        } else {
            linearLayoutSearch.setBackgroundResource(R.drawable.background_search_home);
        }
    }

    private boolean checkLogin() {
        boolean isLogin = Singleton.getInstance().isLogin();
        if (isLogin) {
            cvLoginOrSignUp.setVisibility(View.GONE);
            return true;
        } else {
            cvLoginOrSignUp.setVisibility(View.VISIBLE);
            return false;
        }
    }

    private void init(View view) {
        sharedPreferences = getActivity().getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        imageProfile = view.findViewById(R.id.image_profile);
        linearLayoutSearch = view.findViewById(R.id.linear_layout_search_home);
        btnLogin = view.findViewById(R.id.btn_login_home);
        btnSignUp = view.findViewById(R.id.btn_signup_home);
        cvLoginOrSignUp = view.findViewById(R.id.cv_login_or_signup_home);
        rcvListCoin = view.findViewById(R.id.rcv_list_coin_home);
    }
}