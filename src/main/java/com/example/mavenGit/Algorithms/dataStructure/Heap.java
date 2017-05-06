package com.example.mavenGit.Algorithms.dataStructure;

import com.example.mavenGit.Algorithms.SortUtils;

public class Heap {

	private int[] src;
	private int size;

	public Heap(int[] sr) {
		this.src = sr;
		if (null != src) {
			size = src.length;
		}
		// System.out.println("initHeap=");
		// System.out.println(toString());
		buildHeap();
	}

	public Heap(int[] sr, int sz) {
		this.src = sr;
		size = sz;
		buildHeap();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void buildHeap() {
		if (null == this.src || src.length < 2) {
			return;
		}
		int p = size / 2;
		for (int k = p; k > 0; k--) {
			maxHeapify(src, k);// 构建最大堆
			// System.out.println("maxHeapify:time=" + (p - k + 1));
			// System.out.println(toString());
		}
		return;
	}

	/**
	 * 原理：每一次都保证某个节点下的所有子树都是有序的（根最大）
	 * 
	 * @param A
	 * @param k
	 */
	private void maxHeapify(int[] A, int k) {
		if (null == A || k < 0 || k >= size) {
			return;
		}
		int largest = k;// 最大值下标
		int left = 2 * k + 1;
		int right = 2 * k + 2;
		if (left < size && A[left] > A[largest]) {
			largest = left;
		}
		if (right < size && A[right] > A[largest]) {
			largest = right;
		}
		if (largest != k) {
			SortUtils.swap(A, k, largest);
			maxHeapify(A, largest);
		}
	}

	public static int[] heapSort(int[] src) {
		Heap heap = new Heap(src);// 先构建最大堆
		for (int i = src.length - 1; i > 0; i--) {
			SortUtils.swap(src, 0, i);
			heap.setSize(heap.getSize() - 1);
			heap.maxHeapify(src, 0);
		}
		return heap.src;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Heap[");
		if (this.src != null && size > 0) {
			for (int i = 0; i < src.length; i++) {
				sb.append(src[i]).append("(parent:");
				if (i == 0) {
					sb.append("root");
				} else {
					sb.append((i - 1) / 2);
				}
				sb.append(")");
				if (i < src.length - 1) {
					sb.append(",");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public static void main(String[] args) {
		int[] src = { 91, 2, 45, 7, 10, 56, 9, 3, 8 };
		// Heap heap = new Heap(src);
		// System.out.print(heap.toString());
		int[] result = Heap.heapSort(src);
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]);
			if (i < result.length - 1) {
				System.out.print(",");
			}
		}
	}
}
