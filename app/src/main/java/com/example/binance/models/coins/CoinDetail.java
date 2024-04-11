package com.example.binance.models.coins;

public class CoinDetail extends Coin {
    private String close, high, low, open, volume;
    private int timestamp, closeTimestamp, time;
    public CoinDetail(int id, String symbol) {
        super(id, symbol);
    }
}
