package exercises.counter;

public class Counter {
	private long counter;

	public Counter() {
		counter = 0;
	}

	public synchronized long get() {
		return counter;
	}

	public synchronized void inc() {
		counter++;
	}
}
