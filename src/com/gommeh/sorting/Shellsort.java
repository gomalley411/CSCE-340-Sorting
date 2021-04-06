package com.gommeh.sorting;

class Shellsort extends SortingAlgorithm {

	public void sort(Comparable[] a) {
		System.out.println("Shellsorting...");
		int N = a.length;
		
		int h = 1;
		while (h < N/3) h =3*h+1; // 3x+1 increment sequence
		
		while (h >= 1) { // h-sort the array
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
					exch(a, j, j-h);
				}
			}
			h = h/3;
		}
		System.out.println("Done shellsorting.");
	}
	
}
