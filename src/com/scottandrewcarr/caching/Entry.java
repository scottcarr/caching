package com.scottandrewcarr.caching;

import java.io.Serializable;
import java.util.Random;

public class Entry implements Serializable {
    private byte[] data;
    public Entry() {
        Random gen = new Random(System.currentTimeMillis());
        int n = (Math.abs(gen.nextInt()) % 10000) + 100;
        data = new byte[n*1024];
    }
    public Entry(int n) {
        data = new byte[n];
    }
    public byte read(int n) {
        // just a dummy method so IDE wont complain about
        // data not being used
        return data[n];
    }
}
