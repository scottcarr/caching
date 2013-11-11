package com.scottandrewcarr.caching;

import java.io.Serializable;

public class Entry implements Serializable {
    private String data;
    private double[] reals;
    public Entry(String data) {
        this.data = data;
        reals = new double[50000*3];
    }
}
