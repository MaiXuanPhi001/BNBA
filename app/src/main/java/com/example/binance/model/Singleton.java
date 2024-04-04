package com.example.binance.model;

public class Singleton {
    private static Singleton INSTANCE = null;
    private Profile profile = null;
    private String userName;
    private boolean isLogin = false;
    private boolean tokenChecked = false;
    public static Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public boolean isTokenChecked() {
        return tokenChecked;
    }

    public void setTokenChecked(boolean tokenChecked) {
        this.tokenChecked = tokenChecked;
    }
}
