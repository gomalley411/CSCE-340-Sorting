package com.gommeh.sorting;
public class MergeSort extends SortingAlgorithm {

	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
		for (int k = lo; k <= hi; k++) { // copy
			aux[k] = a[k];
		}
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++) { // merge
			if (i > mid) a[k] = aux[j++];
			else if (j > hi) a[k] = aux[i++];
			else if (less(aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
		}
	}
	
	private static void sort(Comparable[] a, Comparable[] aux, int low, int high) {
		if (high == low) return;
		int mid = low + (high - low) / 2;
		sort(a, aux, low, mid);
		sort(a, aux, mid+1, high);
		
		if (!less(a[mid+1], a[mid])) return;
		
		merge(a, aux, low, mid, high);
	}
	
	public void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];
		sort(a, aux, 0, a.length-1);
	}

}
