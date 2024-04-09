package com.example.binance.screens;

import static com.example.binance.constants.App.COIN;
import static com.example.binance.constants.Service.DOMAIN;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.binance.R;
import com.example.binance.model.Coin;
import com.example.binance.model.Singleton;
import com.example.binance.utils.DemoBase;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

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
        System.out.println("symbol: " + coin.getSymbol());
        connectSocket();
        setChart();
        setDataChart();
    }

    private void connectSocket() {
        socket.on("listCoin", onListCoin);
        socket.connect();
    }

    private Emitter.Listener onListCoin = new Emitter.Listener() {
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
                        System.out.println("abc");
                        if (c.getSymbol().equals(coin.getSymbol())) {
                            float close = c.getClose();
                            float percentChange = c.getPercentChange();
                            tvClose.setText(close + "");
                            tvClose2.setText(close + "");
                            tvPercentChange.setText(percentChange + "");
                            return;
                        }
                    }
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

    private void setDataChart() {
        System.out.println("dang luot");

//        chart.resetTracking();

        ArrayList<CandleEntry> values = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
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