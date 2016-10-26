package com.thu.bigdata.algorithm.externalsort;

public class QuickSort {

	public static int parttion(long[] sort, int begin, int end) {

		long key = sort[begin];
		int left = begin;
		int right = end;
		while (left < right) {
			while (left < right && sort[right] >= key) {
				right--;
			}
			if (left < right) {
				sort[left] = sort[right];
				left++;
			}
			while (left < right && sort[left] <= key) {
				left++;
			}
			if (left < right) {
				sort[right] = sort[left];
				right--;
			}
		}
		sort[left] = key;
		return left;
	}

	public static void quickSort(long[] sort, int begin, int end) {
		int mid;
		if (begin < end) {
			mid = parttion(sort, begin, end);
			quickSort(sort, begin, mid - 1);
			quickSort(sort, mid + 1, end);
		}
	}
	
	public static void display(long[] sort ,int begin,int end){
		for (long l : sort) {
			System.out.print(l+" ");
		}
	}
	
	public static void main(String[] args) {
		
	}

}
