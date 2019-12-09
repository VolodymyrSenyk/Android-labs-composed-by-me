package com.senyk.volodymyr.lab10.models.pojo;

public class CurrencyPojo {
    private String name;
    private double rate;

    public CurrencyPojo(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return this.name;
    }

    public double getRate() {
        return this.rate;
    }
}
