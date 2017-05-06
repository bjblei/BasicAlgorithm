package com.example.mavenGit.Algorithms.sort;

public class InsertSort {

	public InsertSort() {
	}

	public static int[] sort(int[] src) {
		if (null == src || src.length < 2) {
			return src;
		}
		// 长度至少为2
		for (int j = 1; j < src.length; j++) {
			int key = src[j];// the one wait for sorting
			int i = j - 1;
			while (i >= 0 && src[i] > key) {
				src[i + 1] = src[i];
				i--;
			}
			src[i + 1] = key;//!!should use key instead of src[j] which has been override
		}
		return src;
	}

	public static void main(String[] args) {
		int[] src = { 1, 22, 5, 4 };
		int[] result = InsertSort.sort(src);
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]);
			if (i < result.length - 1) {
				System.out.print(",");
			}
		}
	}
}
