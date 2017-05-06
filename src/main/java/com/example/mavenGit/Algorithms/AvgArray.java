package com.example.mavenGit.Algorithms;

/**
 * 是随机数组（1，12，4，7，9）在不改变个数的情况下尽可能均匀分布
 * 
 * @author leibao
 *
 */
public class AvgArray {
	/**
	 * 最后结果如果不完全相等，较大值总是在前面
	 * 
	 * @param src
	 * @return
	 */
	public static int[] averageArray(int[] src) {
		if (null == src || src.length < 2) {
			return src;
		}
		int size = src.length;
		int sum = 0;
		for (int value : src) {
			sum += value;
		}
		int avg = 0;
		avg = sum / size;// 默认能够整除
		if (sum % size == 0) {
			for (int p = 0; p < src.length; p++) {
				src[p] = avg;
			}
			return src;
		}
		int delta = 0;
		for (int i = 0; i < size; i++) {
			int value = src[i];
			if (value > avg) {
				// 注意！这里取多余值从avg开始（最终结果avg和ceil个数不确定，可能最终ceil个数会减少）
				int delta0 = value - avg;
				delta += delta0;
				src[i] = avg;
			}
		}
		int count = 0;
		// 现在所有值都不大于avg
		while (delta > 0) {
			for (int j = 0; j < size; j++) {
				count++;// 由可能遍历完成后delta仍有剩余，此时需要将部分avg变为ceil
				if (src[j] < avg) {
					int need = avg - src[j];
					if (need <= delta) {
						src[j] += need;
						delta -= need;
					} else {
						return null;// 理论上不存在不够的情况（delta从avg上取）
					}
				} else if (delta > 0 && count > size) {// delta仍未分配完毕(第2轮遍历)
					src[j] += 1; // avg变为ceil
					delta -= 1;
				}
			}
		}
		return src;
	}

	/**
	 * 尽可能保证原来的状态
	 * 
	 * @param src
	 * @return
	 */
	public static int[] avgArrayLeastChange(int[] src) {
		if (null == src || src.length < 2) {
			return src;
		}
		int size = src.length;
		int sum = 0;
		for (int value : src) {
			sum += value;
		}
		int avg = 0;
		avg = sum / size;// 默认能够整除
		if (sum % size == 0) {
			for (int p = 0; p < src.length; p++) {
				src[p] = avg;
			}
			return src;
		}
		int ceil = avg + 1;
		int delta = 0;
		for (int i = 0; i < size; i++) {
			int value = src[i];
			if (value > ceil) {
				// 注意！这里取多余值从ceil开始,为保证尽可能和原始分布状态保持一直（最终结果avg和ceil个数不确定，可能最终ceil个数会减少）
				int delta0 = value - ceil;
				delta += delta0;
				src[i] = ceil;
			}
		}
		int less = 0;
		// 只要有比avg小的值，就一直循环
		loop: while (less < size) {
			while (src[less] >= avg) {// avg or avg+1(ceil)
				less++;
				if (less >= size) {
					break loop;
				}
				continue;
			}
			// 需要补齐的位置
			int need = avg - src[less];
			if (delta < need) {// 如果不够补齐，一直到补齐为止
				int count = need - delta;// 此处还需要从ceil再取出个数
				int getNum = 0;
				for (int more = 0; getNum < count && more < src.length; more++) {
					if (src[more] == ceil) {// 现在最大值就是ceil
						delta += 1;
						src[more] -= 1;
						getNum++;
					}
				}
			}
			src[less] += need;
			delta -= need;
			less++;
		}
		return src;
	}

	public static void main(String[] args) {
		int[] src = { 1, 12, 4, 6, 9, 5, 7 };
		// int[] result = AvgArray.averageArray(src);
		int[] result = AvgArray.avgArrayLeastChange(src);
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]);
			if (i < result.length - 1) {
				System.out.print(",");
			}
		}
	}
}
