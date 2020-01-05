package com.example.mavenGit;

import java.util.HashSet;

/**
 * @version 1.0
 * @auth leibao
 * @date 2019/12/13
 */
public class OOMExample {


	public static int fab(int n) {
		if (n <= 2) {
			return 1;
		}
		return fab(n - 1) + fab(n - 2);
	}

	public static void main(String[] args) {
		int k = Integer.MAX_VALUE - 1;
		int m = 1, n = 1;//m,n,m,n,m...
		HashSet set = new HashSet();
		for (int i = 3; i < k; i += 2) {
			//int result = OOMExample.fab(i);
			m = m + n;
			n = m + n;

			set.add(new Integer(i));

			System.out.println("fab(" + i + ")=" + m);

			System.out.println("fab(" + (i + 1) + ")=" + n);

		}

	}
}
