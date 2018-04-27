package com.example.mavenGit.Algorithms.sort;

public class MergeSort {
    public MergeSort() {
    }

    /**
     * 主算法
     *
     * @param A
     * @param p
     * @param r
     * @return
     */
    public static int[] mergeSort(int[] A, int p, int r) {
        // 特殊情况
        if (null == A || A.length < 2) {
            return A;
        }
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(A, p, q);
            mergeSort(A, q + 1, r);
            merge(A, p, q, r);
        }
        return A;
    }

    /**
     * 有序字数组合并
     *
     * @param A
     * @param p
     * @param q
     * @param r
     * @return
     */
    private static int[] merge(int[] A, int p, int q, int r) {
        if (null == A || A.length < 2) {
            return A;
        }
        int[] r1 = new int[q - p + 1];
        int[] r2 = new int[r - q];
        int i = p;
        int j = q + 1;
        while (i <= q) {
            r1[i - p] = A[i];
            i++;
        }
        while (j <= r) {
            r2[j - q - 1] = A[j];
            j++;
        }
        i = 0;
        j = 0;
        int k = p;
        while (i < r1.length && j < r2.length) {
            if (r1[i] < r2[j]) {
                A[k++] = r1[i];
                i++;
            } else {
                A[k++] = r2[j];
                j++;
            }
        }
        while (i < r1.length) {
            A[k++] = r1[i];
            i++;
        }
        while (j < r2.length) {
            A[k++] = r2[j];
            j++;
        }
        return A;
    }

    public static void main(String[] args) {
        int[] src = {1, 22, 5, 4};
        int[] result = MergeSort.mergeSort(src, 0, 3);
        for (int i = 0; i < result.length; i++)
            System.out.println(result[i]);
    }
}
