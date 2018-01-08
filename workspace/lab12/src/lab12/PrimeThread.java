package lab12;

public class PrimeThread extends Thread
{
    private int start;
    private int end;
    private int sum;

    private static boolean isPrime(int x)
    {
      if (x == 0 || x == 1) return false;
      for(int i = 2; i <= Math.sqrt(x); i++)
        if (x % i == 0)
          return false;
      return true;
    }

    public PrimeThread(int start, int end)
    {
      this.start = start;
      this.end = end;
      sum = 0;
    }

    public void run()
    {
      for(int i = start; i < end; i++)
        if (isPrime(i))
          sum++;
      System.out.println("[" + start + ", " + end + ") : " + sum);
    }

    public int getSum() { return this.sum; }
}
