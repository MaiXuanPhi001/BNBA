package com.example.binance.screens;

import static com.example.binance.constants.App.COIN;
import static com.example.binance.constants.App.MAX_NUMBER_CANDLE;
import static com.example.binance.constants.App.TIME_CHART;
import static com.example.binance.constants.Service.BEARER;
import static com.example.binance.constants.Service.DOMAIN;
import static com.example.binance.constants.SocketIO.LIST_COIN;
import static com.example.binance.constants.SocketIO.UPDATESPOT;
import static com.example.binance.constants.Storages.SETTING;
import static com.example.binance.constants.Storages.TOKEN;
import static com.example.binance.utils.NumberFormatter.dotwithcomma;
import static com.example.binance.utils.Utils.getToken;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.binance.R;
import com.example.binance.models.Singleton;
import com.example.binance.models.coins.Coin;
import com.example.binance.models.coins.ResponseGetListCoin;
import com.example.binance.models.getChart.DataResponseGetChart;
import com.example.binance.models.getChart.RequestGetChart;
import com.example.binance.models.getChart.ResponseGetChart;
import com.example.binance.models.users.Profile;
import com.example.binance.services.BinanceService;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TradeActivity extends AppCompatActivity {

    private CandleStickChart chart;
    private TextView tvClose, tvClose2, tvPercentChange;
    private Socket socket;
    private Coin coin;

    {
        try {
            socket = IO.socket(DOMAIN);
        } catch (URISyntaxException e) {
            System.out.println("error socket: " + e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trade);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        coin = (Coin) getIntent().getSerializableExtra(COIN);
        getChartFromAPI();
        System.out.println("symbol: " + coin.getSymbol());
//        connectSocket();
//        setChart();
//        setDataChart();
    }

    private void getChartFromAPI() {
        String token = getToken(TradeActivity.this);
        RequestGetChart requestGetChart = new RequestGetChart(MAX_NUMBER_CANDLE, coin.getSymbol(), TIME_CHART);

        BinanceService.binanceService.getChart(BEARER + " " + token);

//        BinanceService.binanceService.getChart(BEARER + " " + token).enqueue(new Callback<ResponseGetChart>() {
//            @Override
//            public void onResponse(Call<ResponseGetChart> call, Response<ResponseGetChart> response) {
//                // Hàm trả về ResponseLogin có thể khai báo thiếu field nhưng phải đúng tên và kiểu dữ liệu, nếu không đúng sẽ chạy hàm onFailure bên dưới
//                // Vd: response có trả về filed message nhưng không nhất thiết phải khai báo field message trong class ResponseLogin
//                // Vd: balance trong response là kiểu float thì phải khai báo đúng kiểu float, nếu không đúng sẽ chạy hàm onFailure bên dưới
////                if (response.code() == 200) {
////                    DataResponseGetChart dataResponseGetChart = response.body().getData();
////                    Singleton singleton = Singleton.getInstance();
//////                    singleton.setProfile(dataResponseGetChart);
//////                    singleton.setLogin(true);
//////                    SharedPreferences.Editor editor = getSharedPreferences(SETTING, MODE_PRIVATE).edit();
//////                    editor.putString(TOKEN, profile.getToken());
//////                    editor.apply();
//////                    finish();
////                } else {
////                    try {
////                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
////                        String errorMessage = jsonObject.getString("message");
////                        Toast.makeText(TradeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
////                    } catch (JSONException | IOException e) {
////                        e.printStackTrace();
////                    }
////                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseGetChart> call, Throwable t) {
//                Toast.makeText(TradeActivity.this, R.string.Cannot_connect_server, Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void connectSocket() {
        socket.on(LIST_COIN, onListCoin);
        socket.on(coin.getSymbol() + UPDATESPOT, onUpdateSpot);
        socket.connect();
    }

    private final Emitter.Listener onListCoin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];
                    Type listType = new TypeToken<List<Coin>>(){}.getType();
                    // Use Gson to convert JSON array to ArrayList
                    List<Coin> coins = new Gson().fromJson(data.toString(), listType);
                    for (Coin c: coins) {
                        if (c.getSymbol().equals(coin.getSymbol())) {
                            String close = dotwithcomma(c.getClose());
                            float percentChange = c.getPercentChange();
                            tvClose.setText(close);
                            tvClose2.setText("=" + close + "$");
                            if (percentChange >= 0) {
                                tvPercentChange.setTextColor(ContextCompat.getColor(TradeActivity.this, R.color.green_can));
                                tvPercentChange.setText("+" + percentChange + "%");
                            } else {
                                tvPercentChange.setTextColor(ContextCompat.getColor(TradeActivity.this, R.color.red_can));
                                tvPercentChange.setText(percentChange + "%");
                            }
                            return;
                        }
                    }
                }
            });
        }
    };

    private final Emitter.Listener onUpdateSpot = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    System.out.println("abc");
                    JSONArray data = (JSONArray) args[0];
                    Log.e("data: ", String.valueOf(data));
//                    Type listType = new TypeToken<List<Coin>>(){}.getType();
//                    List<Coin> coins = new Gson().fromJson(data.toString(), listType);
                }
            });
        }
    };

    private void setChart() {
        chart.setBackgroundColor(Color.WHITE);

        chart.getDescription().setEnabled(false);

        // nếu có hơn 60 mục được hiển thị trong biểu đồ thì sẽ không có giá trị nào
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setEnabled(false);
        leftAxis.setLabelCount(7, false);
//        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
//        rightAxis.setStartAtZero(false);

        chart.getLegend().setEnabled(false);
    }

    private void setDataChart(List<Coin> coins) {
        System.out.println("dang luot");

//        chart.resetTracking();

        ArrayList<CandleEntry> values = new ArrayList<>();

        for (int i = 0; i < coins.size(); i++) {
            float high = 8f;
            float low = 3f;

            float open = 7f;
            float close = 6f;
            values.add(new CandleEntry(i, high,low,open,close));
        }

        CandleDataSet set1 = new CandleDataSet(values, "Data Set");

        set1.setDrawIcons(false);
//        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(Color.DKGRAY);
        set1.setShadowWidth(0.7f);
        set1.setDecreasingColor(Color.RED);
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(Color.rgb(122, 242, 84));
        set1.setIncreasingPaintStyle(Paint.Style.STROKE);
        set1.setNeutralColor(Color.BLUE);
        set1.setDrawValues(false);
        //set1.setHighlightLineWidth(1f);

        CandleData data = new CandleData(set1);

        chart.setData(data);
        chart.invalidate();
    }

    private void init() {
        chart = findViewById(R.id.chart1);
        tvClose = findViewById(R.id.tv_close_price_trade);
        tvClose2 = findViewById(R.id.tv_close_price_trade2);
        tvPercentChange = findViewById(R.id.tv_percent_change_trade);
    }
}