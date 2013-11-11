package com.scottandrewcarr.caching;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

public class BenchMark {
    private static final int n = 100000;
    private static final int nano = 1000000000;
    private static final int cache_size = 106;
    private static JCS cache;
    private static long t0, t1, longest;
    private static long average;
    public static void main(String[] args) throws Exception {
        try {
            cache = JCS.getInstance("cache");
        } catch (Exception e) {
            System.out.println("Error getting cache instance");
            throw e;
        }
        try {
            // fill the cache with junk
            for (int i = 0; i < cache_size; i++) {
                cache.put(i, new Entry());
            }
            // read from the cache at random, sometimes write
            average = 0;
            longest = 0;
            for (int i = 0; i < n; i++) {
                t0 = System.nanoTime();
                if ((i%1000)==0) {
                    cache.put(i%106, new Entry());
                }
                Entry e = (Entry) cache.get(i%106);
                t1 = System.nanoTime();
                average += t1 - t0;
                if ((t1 - t0) > longest) {
                    longest = t1 - t0;
                }
            }
            System.out.println("Average: " + (double)average / n/ nano);
            System.out.println("Longest: " + (double)longest/ nano);
        } catch (CacheException e) {
            System.out.println("problem inserting entry");
            e.printStackTrace();
            throw e;
        }
    }
}
