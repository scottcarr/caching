package com.scottandrewcarr.caching;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import java.util.Random;

public class BenchMark {
    private static final int nano = 1000000000;
    private static double size_mean;
    private static double size_variance;
    private static int n_iter;
    private static int cache_size;
    private static double prob_put;
    private static JCS cache;
    private static long t0, t1, longest;
    private static long average;
    private static Random rand_gen;
    private static double key_variance;
    public static void main(String[] args) throws Exception {
        if (args.length == 6) {
            prob_put = Double.parseDouble(args[0]);
            n_iter = Integer.parseInt(args[1]);
            cache_size  = Integer.parseInt(args[2]);
            size_mean  = Integer.parseInt(args[3]);
            size_variance  = Integer.parseInt(args[4]);
            key_variance = Integer.parseInt(args[5]);
            rand_gen = new Random(System.currentTimeMillis());
        } else {
            System.out.println("USAGE: BenchMark prob_put n_iter cache_size size_mean size_variance key_variance");
            return;
        }
        try {
            cache = JCS.getInstance("cache");
        } catch (Exception e) {
            System.out.println("Error getting cache instance");
            throw e;
        }
        try {
            // fill the cache with junk
            for (int i = 0; i < cache_size; i++) {
                //cache.put(i, new Entry());
                cache.put(i, new Entry(rand_gen.nextGaussian(), size_mean, size_variance));
            }
            // read from the cache at random, sometimes write
            average = 0;
            longest = 0;
            for (int i = 0; i < n_iter; i++) {
                t0 = System.nanoTime();
                //if ((i%1000)==0) {
                if (shouldPut()) {
                    //cache.put(i%106, new Entry());
                    //cache.put(i%cache_size, new Entry(rand_gen.nextGaussian(), size_mean, size_variance));
                    cache.put(getNormDistKey(), new Entry(rand_gen.nextGaussian(), size_mean, size_variance));
                }
                //Entry e = (Entry) cache.get(i%cache_size);
                cache.get(getNormDistKey());
                t1 = System.nanoTime();
                average += t1 - t0;
                if ((t1 - t0) > longest) {
                    longest = t1 - t0;
                }
            }
            System.out.println("Average: " + (double)average / n_iter / nano);
            System.out.println("Longest: " + (double)longest/nano);
        } catch (CacheException e) {
            System.out.println("problem inserting entry");
            e.printStackTrace();
            throw e;
        }
    }
    public static boolean shouldPut() {
        return prob_put > rand_gen.nextDouble();
    }
   public static int getNormDistKey() {
       double middle_key = cache_size / 2;
       return (int) (rand_gen.nextGaussian() * key_variance + middle_key);

   }
}
