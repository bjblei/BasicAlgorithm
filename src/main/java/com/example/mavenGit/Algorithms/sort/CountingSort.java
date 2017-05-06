package com.example.mavenGit.Algorithms.sort;

public class CountingSort {
	public CountingSort() {
	}

	/**
	 * 
	 * @param A
	 *            输入原始整数数组：均不小于0
	 * @return 排序后的结果
	 */
	public static int[] sort(int[] A) {
		if (null == A || A.length < 2) {
			return A;
		}
		int[] B = new int[A.length];// B:排序结果
		// 1:获取A中最大正整数
		int max = 0;
		for (int i = 0; i < A.length; i++) {
			if (A[i] < 0) {
				return null;// 不允许出现负数
			}
			max = Math.max(max, A[i]);
		}
		if (max == 0) {
			return A;
		}
		// 2:C：存放统计结果
		// 2.1 初始化C数组
		int[] C = new int[max + 1];// 0~max
		for (int j = 0; j < C.length; j++) {
			C[j] = 0;
		}
		// 2.2:统计A中元素数目,放入C中对应位置
		for (int k = 0; k < A.length; k++) {
			C[A[k]] += 1;
		}
		// 3:累加C中计数
		for (int p = 1; p < C.length; p++) {
			C[p] += C[p - 1];
		}
		// 4:copyA中数据到B中对应位置
		for (int q = A.length - 1; q >= 0; q--) {// q>=1
			B[C[A[q]] - 1] = A[q];// A中值A[q]（已存在）,是C数组中下标,C[A[q]]是累计个数(必然>=1)，也对应B中位置index+1
			C[A[q]] -= 1;
		}
		return B;
	}

	public static void main(String[] args) {
		int[] src = { 1, 29, 3, 0, 8, 0, 89, 2, 45, 9, 4, 8 };
		int[] result = CountingSort.sort(src);
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]);
			if (i < result.length - 1) {
				System.out.print(",");
			}
		}
	}
}
