package com.gommeh.sorting;

public class Quicksort extends SortingAlgorithm {

	private static int partition(Comparable[] a, int lo, int hi) {
		int i = lo, j = hi+1;

		while (true) {
			// find item on left to swap
			while (less(a[++i], a[0])) if (i == hi) break;

			// find item on right to swap
			while (less(a[lo], a[--j])) if (j == lo) break;

			if (i >= j) break; // check if pointers cross
			exch(a, i, j); // swap	
		}

		exch(a, lo, j); // swap with partitioning item
		return j; // return index of item now known to be in place

	}

	public void sort(Comparable[] a) {
		StdRandom.shuffle(a); // shuffle needed for performance guarantee
		sort(a, 0, a.length -1);
	}

	private void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo) return;
		int j = partition(a, lo, hi);
		sort(a, lo, j-1);
		sort(a, j+1, hi);
	}
}


