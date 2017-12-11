package exercises.biggestvalue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class BiggestValueForkJoinPool {

	public static Integer findBiggestValue(int[] arr) throws InterruptedException, ExecutionException {
		ForkJoinPool pool = ForkJoinPool.commonPool();
		BiggestValueAction bgv = new BiggestValueAction(arr, 0, arr.length - 1);
		pool.invoke(bgv);
		return bgv.get();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int[] arr = {435, 232, 54, -4, 1, 32565, 30, 5,  64, 3};
		System.out.println(findBiggestValue(arr));
	}
}

class BiggestValueAction extends RecursiveTask<Integer> {
	private int[] arr;
	private int left;
	private int right;
	
	public BiggestValueAction(int[] arr, int left, int right) {
		this.arr = arr;
		this.left = left;
		this.right = right;
	}

	@Override
	protected Integer compute() {
		if(left < right) {
			int middle = (left + right) / 2;
			BiggestValueAction leftTh = new BiggestValueAction(arr, left, middle);
			BiggestValueAction rightTh = new BiggestValueAction(arr, middle + 1, right);
			invokeAll(leftTh, rightTh);
			try {
				return Math.max(leftTh.get(), rightTh.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}
		return arr[left];
	}
}
