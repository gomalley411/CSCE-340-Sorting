package com.gommeh.sorting;

public class SortingAlgorithm {
	
	/**
	 * the actual individual sorting algorithm's code, to be filled in when writing each type of algorithm
	 * @param a = the array
	 */
	void sort(Comparable[] a) {}

	/**
	 * compareTo() Returns a negative integer, zero, or a positive 
	 * integer as v is less than, equal to, or greater than w.
	 * @param v
	 * @param w
	 * @return
	 */
	static boolean less(Comparable<Comparable<?>> v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	/**
	 * switches two elements in the Comparable with each other
	 * @param a = the array
	 * @param i and @param j = the two elements
	 */
	static void exch(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	/** print the array on a single line
	 * @param a = the array
	 */
	public static void show(Comparable[] a) {
		System.out.println("Printing...");
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}
	
	/**
	 * tests whether the array entries are in order
	 * @param a = the array
	 * @return false if they are not, true if they are
	 */
	static boolean isSorted(Comparable[] a) {
		System.out.println("Determining whether the array is sorted...");
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i-1])) {
				System.out.println("The array in question has not been sorted.");
				return false;
			}
		}
		System.out.println("The array in question has been sorted. :)");
		return true;
	}
}

