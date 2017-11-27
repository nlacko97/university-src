
public class MergeThread extends Thread {
	
	private int left;
	private int right;
	public int[] arr;
	
	public MergeThread(int[] arr, int left, int right) {
		this.left = left;
		this.right = right;
		this.arr = arr;
	}
	
	public void run() {
		MergeSort.mergeSort(arr, left, right);
	}
}
