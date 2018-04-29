package com.example.mavenGit.Algorithms;

/**
 * @author leibao
 */
public class SortUtils {
    /**
     * swap
     *
     * @param src
     * @param p
     * @param k
     */
    public static void swap(int[] src, int p, int k) {
        if (null == src || src.length < 2 || p == k) {
            return;
        }
        if (p < 0 || p >= src.length || k <= 0 || k >= src.length) {
            return;
        }
        int pv = src[p];
        src[p] = src[k];
        src[k] = pv;
        return;
    }

    public static void printArray(int[] result) {
        System.out.print("[");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);
            if (i < result.length - 1) {
                System.out.print(",");
            }
        }
        System.out.print("]");
    }

    public static void printArray(Object[] result) {
        System.out.print("[");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);
            if (i < result.length - 1) {
                System.out.print(",");
            }
        }
        System.out.print("]");
    }
}
