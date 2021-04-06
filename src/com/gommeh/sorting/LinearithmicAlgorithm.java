package com.gommeh.sorting;
public class LinearithmicAlgorithm {

	public static void linearithmic(int N) {
		if (N == 0) return;
		
		// solve two problems of half the size
		linearithmic(N/2);
		linearithmic(N/2);
		
		// linear(N); // do a linear amount of work
	}

}
