
public class Pair implements Comparable<Pair> {
	public int key;
	public Object val;
	
	public Pair(int key, Object val)
	{
		this.key = key;
		this.val = val;
	}

	@Override
	public int compareTo(Pair a) {
		int x = this.key;
		int y = a.key;
		if (x > y)
			return 1;
		else if (x < y)
			return -1;
		else
		return 0;
	}
	
	public String toString()
	{
		return this.key + ".: " + this.val;
	}
}
