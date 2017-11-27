
public class MergeSort {
	
	public static void mergeSort(int[] arr) 
	{
		int nrThreads = Runtime.getRuntime().availableProcessors();
		int p = arr.length / nrThreads;
		MergeThread[] t = new MergeThread[nrThreads];
		for(int i = 0; i < nrThreads; i++)
		{
			int left = i * p;
			int right = Math.min(left + p, arr.length - 1);
			t[i] = new MergeThread(arr, left, right);
			t[i].start();
		}
		
		for(int i = 0; i < nrThreads; i++) {
			try {
				t[i].join();
			} catch (InterruptedException e) {}
		}
		
		int middle = p - 1;
		int right = 2 * p - 1;
		for(int i = 1; i < nrThreads; i++)
		{
			merge(arr, 0, middle, right);
			middle = right;
			if (i < nrThreads - 2) {
				right = middle + p;
			}
			else {
				right = arr.length - 1;
			}
		}
	}

	public static void mergeSort(int[] arr, int left, int right) 
	{
		// System.out.println(left + "\t" + right);
		// the array can be split
		if (left < right) {
			int middle = (left + right) / 2;
			// we recursively call mergeSort on the two halves
			mergeSort(arr, left, middle);
			mergeSort(arr, middle + 1, right);
			merge(arr, left, middle, right);
		}
	}

	public static void merge(int[] arr, int left, int endFirstHalf, int right) 
	{
		int i = left;
		int j = endFirstHalf + 1;// beginning of second half
		int k = left;// initial position of the temp array
		int[] tmpArr = new int[arr.length];
		while (i <= endFirstHalf && j <= right) {
			if (arr[i] <= arr[j]) {
				tmpArr[k++] = arr[i++];
			} else {
				tmpArr[k++] = arr[j++];
			}
		}
		// we place those remaining in the first half
		while (i <= endFirstHalf) {
			tmpArr[k++] = arr[i++];
		}
		// we place those remaining in the second half
		while (j <= right) {
			tmpArr[k++] = arr[j++];
		}
		// we copy all the elements in the original array
		for (k = left; k <= right; k++) {
			arr[k] = tmpArr[k];
		}
	}
	
	public static void main(String args[])
	{
		int[] arr = {24, 65, 34, -5, 2, 7, -65, 5};
		System.out.println("Array before merging:");
		for(int i = 0; i < arr.length; i++)
		{
			System.out.print(arr[i] + " ");
		}
		
		
		mergeSort(arr);
		
		
		System.out.println("\n\nArray after merging:");
		for(int i = 0; i < arr.length; i++)
		{
			System.out.print(arr[i] + " ");
		}
		
	}
	
}
