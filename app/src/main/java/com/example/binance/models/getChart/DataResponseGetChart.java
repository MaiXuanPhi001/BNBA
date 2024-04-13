package com.example.binance.models.getChart;

import com.example.binance.models.coins.CoinDetail;

import java.util.List;

public class DataResponseGetChart {
    private List<CoinDetail> coinDetails;

    public DataResponseGetChart(List<CoinDetail> coinDetails) {
        this.coinDetails = coinDetails;
    }

    public List<CoinDetail> getCoinDetails() {
        return coinDetails;
    }

    public void setCoinDetails(List<CoinDetail> coinDetails) {
        this.coinDetails = coinDetails;
    }
}
