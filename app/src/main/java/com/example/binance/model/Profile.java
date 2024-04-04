package com.example.binance.model;

public class Profile {
    private int id;
    private String userName;
    private String email;
    private float balance;
    private String token;
    public Profile(int id, String userName, String email, float balance, String token) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.balance = balance;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
