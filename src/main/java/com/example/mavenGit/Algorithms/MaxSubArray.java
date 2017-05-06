package com.example.mavenGit.Algorithms;

public class MaxSubArray {
	public MaxSubArray() {
	}

	public static int[] findMaxSubArray(int[] src, int p, int r) {
		if (p == r || src.length < 2) {
			return src;
		}
		if (r == p + 1) {
			if(src[p]>0&&src[r]>0){
				return new int[]{src[p],src[r]};
			}
			return null;
					
		}
		int mid = (p + r) / 2;

		return null;
	}

	public static void main(String[] args) {
		int[] src = new int[] { 1, 2, 3, -4, 5, -8, 89, 12, -2, 90 };
		int[] result = MaxSubArray.findMaxSubArray(src, 0, 9);

	}
}
