package exercises.biggestvalue;

import java.util.stream.IntStream;

public class BiggestValueStreams {

	public static int findBiggestValue(int[] arr) {
		return IntStream.of(arr).parallel().max().getAsInt();
	}

	public static void main(String[] args) {
		int[] arr = { 435, 232, 54, -4, 1, 32565, 30, 5, 64, 3 };
		System.out.println(findBiggestValue(arr));
	}
}
