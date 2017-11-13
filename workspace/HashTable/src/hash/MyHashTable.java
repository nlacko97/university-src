package hash;

import java.util.ArrayList;

public class MyHashTable {

	private static final int MAX_BUCKETS = 35;
	private ArrayList<Pair>[] buckets;
	
	@SuppressWarnings("unchecked")
	public MyHashTable()
	{
		buckets = new ArrayList[MAX_BUCKETS];
	}

	private int hash(String key) 
	{
		return Math.abs(key.hashCode() % MAX_BUCKETS);
	}
	
	public void set(String key, Object value)
	{
		Pair pair = new Pair(key, value);
		int index = hash(key);
		if (buckets[index] == null)
		{
			buckets[index] = new ArrayList<Pair>();
		}
		buckets[index].add(pair);
		
	}
	
	public Object get(String key)
	{
		int index = hash(key);
		int i = 0;
		if (buckets[index] != null)
		{			
			while(i < buckets[index].size())
			{
				if (key.equals(buckets[index].get(i).key))
				{
					return buckets[index].get(i).val;
				}
				i++;
			}
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		MyHashTable MHT = new MyHashTable();
		MHT.set("dairy", 25);
		MHT.set("dairk", 654);
		Object value;
		if ((value = MHT.get("dairk")) != null)
		{
			System.out.println(value);
		}
	}

}
