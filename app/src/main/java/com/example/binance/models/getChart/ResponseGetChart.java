package com.example.binance.models.getChart;

import com.example.binance.models.ResponseAPI;

public class ResponseGetChart extends ResponseAPI {
    private DataResponseGetChart data; // có field này là crash

    public ResponseGetChart(String message, boolean status) {
        super(message, status);
    }

    public DataResponseGetChart getData() {
        return data;
    }

    public void setData(DataResponseGetChart data) {
        this.data = data;
    }
}
