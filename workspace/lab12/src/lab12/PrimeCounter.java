package lab12;

public class PrimeCounter {

	int nrThreads;
	public int start;
	public int end;
	private int sum;

	public PrimeCounter(int start, int end)
	{
		this.start = start;
		this.end = end;
		nrThreads = Runtime.getRuntime().availableProcessors();
		sum = 0;
	}

	public int countPrimes()
	{
		PrimeThread[] t = new PrimeThread[nrThreads];
		int partition = (end - start + 1) / nrThreads;
		for(int i = 0; i < nrThreads; i++)
		{
			int e = start + (i + 1) * partition;
			if (i == nrThreads - 1 && e < end) e = end;
			t[i] = new PrimeThread(start + i * partition, e);
			t[i].start();
		}
		for(int i = 0; i < nrThreads; i++)
		{
			try {
				t[i].join();
			} catch (InterruptedException e) {}
		}
		for(int i = 0; i < nrThreads; i++)
		{
			sum += t[i].getSum();
		}
		return sum;
	}

	public static void main(String[] args) {
		PrimeCounter pc = new PrimeCounter(0, 100000);
		System.out.println("Primes in range [" + pc.start + ", " + pc.end + "] = " + pc.countPrimes());
	}
}
