package com.example.binance.models.coins;

import java.io.Serializable;

public class Coin implements Serializable {
    private int id;
    private String symbol;
    private float close;
    private float percentChange;

    public Coin(int id, String symbol, float close, float percentChange) {
        this.id = id;
        this.symbol = symbol;
        this.close = close;
        this.percentChange = percentChange;
    }

    public Coin(int id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(float percentChange) {
        this.percentChange = percentChange;
    }
}
