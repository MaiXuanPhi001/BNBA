package com.example.binance.test;

import java.util.List;

public class UserTest {
    private int id;
    private String name;
    private boolean isActive;
    private JobTest job;
    private List<FavoriteTest> favorites;

    public UserTest(int id, String name, boolean isActive, JobTest job, List<FavoriteTest> favorites) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.job = job;
        this.favorites = favorites;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public JobTest getJob() {
        return job;
    }

    public void setJob(JobTest job) {
        this.job = job;
    }

    public List<FavoriteTest> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavoriteTest> favorites) {
        this.favorites = favorites;
    }
}
