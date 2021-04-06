package com.gommeh.sorting;

public class KnuthShuffle extends SortingAlgorithm {

	public void sort (Object[] a) {
		int N = a.length;
		
		for (int i = 0; i < N; i++) {
			int r = StdRandom.uniform(i+1); // between 0 and i
			exch((Comparable[]) a, i, r);
		}
	}

}
