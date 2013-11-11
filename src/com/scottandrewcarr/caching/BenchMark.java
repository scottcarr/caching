package com.scottandrewcarr.caching;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

public class BenchMark {
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
            for (int i = 0; i < 106; i++) {
                cache.put(i, new Entry("world"));
            }
            // read from the cache at random
            average = 0;
            longest = 0;
            for (int i = 0; i < 10000; i++) {
                t0 = System.nanoTime();
                //if (i==33333345) {
                if ((i%10000)==0) {
                    cache.put(i%106, new Entry("world"));
                }
                Entry e = (Entry) cache.get(i%106);
                t1 = System.nanoTime();
                average += t1 - t0;
                if ((t1 - t0) > longest) {
                    longest = t1 - t0;
                }
            }
            System.out.println("Average: " + (double)average/10000/1000000000);
            System.out.println("Longest: " + (double)longest/1000000000);
        } catch (CacheException e) {
            System.out.println("problem inserting entry");
            e.printStackTrace();
        }
    }
}
