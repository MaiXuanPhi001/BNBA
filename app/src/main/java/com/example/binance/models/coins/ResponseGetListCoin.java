package com.example.binance.models.coins;

import java.util.List;

public class ResponseGetListCoin {
    private String message;
    private boolean status;
    private List<Coin> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Coin> getData() {
        return data;
    }

    public void setData(List<Coin> data) {
        this.data = data;
    }
}
