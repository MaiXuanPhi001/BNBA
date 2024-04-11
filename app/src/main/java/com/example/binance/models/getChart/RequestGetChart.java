package com.example.binance.models.getChart;

public class RequestGetChart {
    private int limit;
    private String symbol;
    private int time;

    public RequestGetChart(int limit, String symbol, int time) {
        this.limit = limit;
        this.symbol = symbol;
        this.time = time;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
