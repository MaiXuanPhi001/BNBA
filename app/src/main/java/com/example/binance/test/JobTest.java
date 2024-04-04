package com.example.binance.test;

public class JobTest {
    private int id;
    private String job;

    public JobTest(int id, String job) {
        this.id = id;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
