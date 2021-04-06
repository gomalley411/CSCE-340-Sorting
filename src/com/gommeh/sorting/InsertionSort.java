package com.gommeh.sorting;

public class InsertionSort extends SortingAlgorithm {
	public void sort(Comparable[] a) {
		System.out.println("Insertion sorting...");
		int N = a.length;
		for (int i = 0; i < N; i++)
			for (int j = i; j > 0; j--)
				if (less(a[j], a[j-1]))
					exch(a, j, j-1);
				else break;
		System.out.println("Done insertion sorting.");
	}
}
