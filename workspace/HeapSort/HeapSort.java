import java.util.Random;

public class HeapSort {

	public static void siftDown(int[] arr, int ind, int size) {
		int maxi  = ind;
		int left  = ind * 2 + 1;
		int right = ind * 2 + 2;

		if (left < size && arr[left] > arr[maxi]) {
			maxi = left;
		}

		if (right < size && arr[right] > arr[maxi]) {
			maxi = right;
		}

		if (maxi != ind) {
			int tmp = arr[ind];
			arr[ind] = arr[maxi];
			arr[maxi] = tmp;

			siftDown(arr, maxi, size - 1);
		}

	}

	public static void heapSort(int[] arr) {
		heapify(arr);
		int end = arr.length - 1;
		while(end > 0) {
			// swap
			int tmp = arr[0];
			arr[0] = arr[end];
			arr[end] = tmp;
			// endswap
			siftDown(arr, 0, end );
			end--;
		}
	}

	public static void heapify(int[] arr) {
		int size  = arr.length;
		int start = ( size - 2 ) / 2;

		while(start >= 0) {
			siftDown(arr, start, size - 1);
			start--;
		} 
	}

	public static void writeArray(int[] a) {
		System.out.print("[ ");
		for(int x : a) {
			System.out.print(x);
			if (x != a[a.length - 1])
				System.out.print(" : ");
		}
		System.out.println(" ]");
	}

	public static void main(String[] args) {
		
		int[] arr = new int [10];
		Random r = new Random();
		for(int i = 0; i < arr.length; i++) {
			arr[i] = r.nextInt() % 200;
		}
		
		System.out.println("\nInput array:");

		writeArray(arr);

		heapSort(arr);

		System.out.println("\nSorted array:");

		writeArray(arr);
	}
}