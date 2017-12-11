package exercises.biggestvalue;

import java.util.concurrent.ExecutionException;

public class BiggestValueThreads {

	public static int findBiggestValue(int[] arr) throws InterruptedException, ExecutionException {
		FindBiggest fb = new FindBiggest(arr, 0, arr.length - 1);
		fb.start();
		fb.join();
		return fb.getMax();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int[] arr = { 435, 232, 54, -4, 1, 32565, 30, 5, 64, 3 };
		System.out.println(findBiggestValue(arr));
	}
}

class FindBiggest extends Thread {
	private int[] arr;
	private int left;
	private int right;
	private int max;

	public FindBiggest(int[] arr, int left, int right) {
		this.arr = arr;
		this.left = left;
		this.right = right;
	}

	@Override
	public void run() {
		if (left < right) {
			int middle = (left + right) / 2;
			FindBiggest leftTh = new FindBiggest(arr, left, middle);
			FindBiggest rightTh = new FindBiggest(arr, middle + 1, right);
			leftTh.start();
			rightTh.start();
			try {
				leftTh.join();
				rightTh.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			max = Math.max(leftTh.max, rightTh.max);
		} else {
			max = arr[left];
		}
	}

	public int getMax() {
		return max;
	}
}
